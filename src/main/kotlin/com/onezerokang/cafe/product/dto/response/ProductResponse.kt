package com.onezerokang.cafe.product.dto.response

import com.onezerokang.cafe.member.dto.response.MemberResponse
import com.onezerokang.cafe.product.domain.Product
import com.onezerokang.cafe.product.domain.ProductCategory
import com.onezerokang.cafe.product.domain.ProductSize
import java.time.LocalDate

data class ProductResponse(
    val id: Long,

    val name: String,

    val description: String,

    val barcode: String,

    val salePrice: Int,

    val originalPrice: Int,

    val expirationDate: LocalDate,

    val category: ProductCategory,

    val size: ProductSize,

    val member: MemberResponse,
) {
    companion object {
        fun of(product: Product): ProductResponse {
            return ProductResponse(
                id = product.id!!,
                name = product.name,
                description=product.description,
                barcode=product.barcode,
                salePrice=product.salePrice,
                originalPrice=product.originalPrice,
                expirationDate=product.expirationDate,
                category=product.category,
                size=product.size,
                member = MemberResponse.of(member = product.member)
            )
        }
    }
}
