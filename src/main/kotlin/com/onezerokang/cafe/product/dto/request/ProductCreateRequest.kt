package com.onezerokang.cafe.product.dto.request

import com.onezerokang.cafe.global.util.KoreanInitialExtractor
import com.onezerokang.cafe.member.domain.Member
import com.onezerokang.cafe.product.domain.Product
import com.onezerokang.cafe.product.domain.ProductCategory
import com.onezerokang.cafe.product.domain.ProductSize
import java.time.LocalDate

data class ProductCreateRequest(
    val name: String,

    val description: String,

    val barcode: String,

    val salePrice: Int,

    val originalPrice: Int,

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
