package com.onezerokang.cafe.product.service

import com.onezerokang.cafe.member.domain.Member
import com.onezerokang.cafe.member.domain.MemberRepository
import com.onezerokang.cafe.member.exception.MemberNotFoundException
import com.onezerokang.cafe.product.domain.Product
import com.onezerokang.cafe.product.domain.ProductCategory
import com.onezerokang.cafe.product.domain.ProductRepository
import com.onezerokang.cafe.product.domain.ProductSize
import com.onezerokang.cafe.product.dto.request.ProductCreateRequest
import com.onezerokang.cafe.product.exception.BarcodeAlreadyRegisteredException
import com.onezerokang.cafe.product.exception.ProductNotFoundException
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.groups.Tuple
import org.assertj.core.groups.Tuple.tuple
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class ProductServiceTest @Autowired constructor(
    private val productService: ProductService,
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
) {

    @DisplayName("상품 등록에 성공할 경우, 상품 초성도 함께 등록된다.")
    @Test
    fun createProduct() {
        // given
        val request = ProductCreateRequest(
            name = "아메리카노",
            description = "아이스",
            barcode = "1234123412341",
            salePrice = 3500,
            originalPrice = 4000,
            expirationDate = LocalDate.of(2024, 12, 31),
            category = ProductCategory.DRINK,
            size = ProductSize.LARGE,
        )

        val member = memberRepository.save(Member(phoneNumber = "01012341234", password = "1234"))

        // when
        productService.createProduct(request, member.id!!)

        //then
        val products = productRepository.findAll()
        assertThat(products).hasSize(1)
            .extracting(
                "name",
                "initialName",
                "description",
                "barcode",
                "salePrice",
                "originalPrice",
                "expirationDate",
                "category",
                "size"
            )
            .containsExactlyInAnyOrder(
                tuple(
                    "아메리카노",
                    "ㅇㅁㄹㅋㄴ",
                    "아이스",
                    "1234123412341",
                    3500,
                    4000,
                    LocalDate.of(2024, 12, 31),
                    ProductCategory.DRINK,
                    ProductSize.LARGE
                )
            )
    }

    @DisplayName("이미 등록된 바코드가 있을 경우, 상품 등록에 실패한다.")
    @Test
    fun createProductWithAllReadyRegisteredBarcode() {
        // given
        val request = ProductCreateRequest(
            name = "아메리카노",
            description = "아이스",
            barcode = "1234123412341",
            salePrice = 3500,
            originalPrice = 4000,
            expirationDate = LocalDate.of(2024, 12, 31),
            category = ProductCategory.DRINK,
            size = ProductSize.LARGE,
        )

        val member = memberRepository.save(Member(phoneNumber = "01012341234", password = "1234"))
        saveProduct(barcode = "1234123412341", member = member)

        // when then
        assertThatThrownBy { productService.createProduct(request, member.id!!) }
            .isInstanceOf(BarcodeAlreadyRegisteredException::class.java)
            .hasMessage("The barcode is already registered.")

    }

    @DisplayName("등록되지 않은 회원은 상품을 등록할 수 없다.")
    @Test
    fun createProductWithUnRegisteredMember() {
        // given
        val memberId = 1L
        val request = ProductCreateRequest(
            name = "아메리카노",
            description = "아이스",
            barcode = "1234123412341",
            salePrice = 3500,
            originalPrice = 4000,
            expirationDate = LocalDate.of(2024, 12, 31),
            category = ProductCategory.DRINK,
            size = ProductSize.LARGE,
        )

        // when then
        assertThatThrownBy { productService.createProduct(request, memberId) }
            .isInstanceOf(MemberNotFoundException::class.java)
            .hasMessage("Member not found")
    }

    // 상품 조회
    @DisplayName("상품 상세 내역을 조회할 수 있다.")
    @Test
    fun getProduct() {
        // given
        val member = memberRepository.save(Member(phoneNumber = "01012341234", password = "1234"))
        val productId = saveProduct(member = member)

        // when
        val response = productService.getProduct(productId = productId, memberId = member.id!!)

        //then
        assertThat(response)
            .extracting(
                "name",
                "description",
                "barcode",
                "salePrice",
                "originalPrice",
                "expirationDate",
                "category",
                "size"
            )
            .containsExactlyInAnyOrder(
                "아메리카노",
                "아이스",
                "1234123412341",
                4000,
                5000,
                LocalDate.of(2024, 12, 31),
                ProductCategory.DRINK,
                ProductSize.LARGE
            )
        assertThat(response.member.phoneNumber).isEqualTo("01012341234")
    }

    @DisplayName("등록되지 않은 상품 상세 내역을 조회할 수 없다.")
    @Test
    fun getProductByUnRegisteredProductId() {
        // given
        val member = memberRepository.save(Member(phoneNumber = "01012341234", password = "1234"))
        val productId = -1L
        // when then
        assertThatThrownBy { productService.getProduct(productId = productId, memberId = member.id!!) }
            .isInstanceOf(ProductNotFoundException::class.java)
            .hasMessage("Product not found")
    }


    @DisplayName("다른 회원이 등록한 상품은 조회할 수 없다.")
    @Test
    fun getProductNotOwnedByMember() {
        // given
        val member = memberRepository.save(Member(phoneNumber = "01012341234", password = "1234"))
        val productId = saveProduct(member = member)
        val memberId = -1L
        // when then
        assertThatThrownBy { productService.getProduct(productId = productId, memberId = memberId) }
            .isInstanceOf(ProductNotFoundException::class.java)
            .hasMessage("Product not found")
    }
//

    private fun saveProduct(
        name: String = "아메리카노",
        initialName: String = "ㅇㅁㄹㅋㄴ",
        description: String = "아이스",
        barcode: String = "1234123412341",
        salePrice: Int = 4000,
        originalPrice: Int = 5000,
        expirationDate: LocalDate = LocalDate.of(2024, 12, 31),
        category: ProductCategory = ProductCategory.DRINK,
        size: ProductSize = ProductSize.LARGE,
        member: Member,
    ): Long {
        val product = Product(
            name = name,
            initialName = initialName,
            description = description,
            barcode = barcode,
            salePrice = salePrice,
            originalPrice = originalPrice,
            expirationDate = expirationDate,
            category = category,
            size = size,
            member = member,

            )
        val savedProduct = productRepository.save(product)
        return savedProduct.id!!
    }

}