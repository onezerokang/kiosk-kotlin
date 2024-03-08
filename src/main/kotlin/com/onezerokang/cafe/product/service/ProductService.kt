package com.onezerokang.cafe.product.service

import com.onezerokang.cafe.member.domain.MemberRepository
import com.onezerokang.cafe.member.exception.MemberNotFoundException
import com.onezerokang.cafe.product.domain.ProductRepository
import com.onezerokang.cafe.product.dto.request.ProductCreateRequest
import com.onezerokang.cafe.product.dto.request.ProductUpdateRequest
import com.onezerokang.cafe.product.dto.response.ProductResponse
import com.onezerokang.cafe.product.exception.BarcodeAlreadyRegisteredException
import com.onezerokang.cafe.product.exception.ProductNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun createProduct(request: ProductCreateRequest, memberId: Long) {
        val isExist = productRepository.existsByBarcode(request.barcode)
        if (isExist) {
            throw BarcodeAlreadyRegisteredException()
        }

        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw MemberNotFoundException()


        val product = request.toEntity(member = member)
        productRepository.save(product)
    }

    // 상품 상세 조회
    fun getProduct(productId: Long, memberId: Long): ProductResponse {
        val product = (productRepository.findByIdAndMemberId(productId = productId, memberId = memberId)
            ?: throw ProductNotFoundException())

        return ProductResponse.of(product = product)
    }

    // 상품 수정
    @Transactional
    fun updateProduct(request: ProductUpdateRequest, productId: Long, memberId: Long) {
        val product = productRepository.findByIdAndMemberId(productId = productId, memberId = memberId)
            ?: throw ProductNotFoundException()
        product.update(request)
    }

    @Transactional
    fun deleteProduct(productId: Long, memberId: Long) {
        val product = productRepository.findByIdAndMemberId(productId = productId, memberId = memberId)
            ?: throw ProductNotFoundException()

        product.delete()
    }
}