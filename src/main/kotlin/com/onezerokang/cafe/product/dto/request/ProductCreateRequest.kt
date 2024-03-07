package com.onezerokang.cafe.product.dto.request

import com.onezerokang.cafe.global.util.KoreanInitialExtractor
import com.onezerokang.cafe.member.domain.Member
import com.onezerokang.cafe.product.domain.Product
import com.onezerokang.cafe.product.domain.ProductCategory
import com.onezerokang.cafe.product.domain.ProductSize
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.time.LocalDate

data class ProductCreateRequest(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val description: String,

    @field:NotBlank
    val barcode: String,

    @field:Positive
    val salePrice: Int,

    @field:Positive
    val originalPrice: Int,

    @field:FutureOrPresent
    val expirationDate: LocalDate,

    val category: ProductCategory,

    val size: ProductSize,
) {

    fun toEntity(member: Member): Product {
        return Product(
            name = name,
            initialName = KoreanInitialExtractor.extract(name),
            description = description,
            barcode = barcode,
            salePrice = salePrice,
            originalPrice = originalPrice,
            expirationDate = expirationDate,
            category = category,
            size = size,
            member = member,
        )
    }
}
