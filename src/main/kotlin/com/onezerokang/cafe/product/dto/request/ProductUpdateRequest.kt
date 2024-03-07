package com.onezerokang.cafe.product.dto.request

import com.onezerokang.cafe.product.domain.ProductCategory
import com.onezerokang.cafe.product.domain.ProductSize
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.time.LocalDate

data class ProductUpdateRequest(

    @field:NotBlank
    val name: String? = null,

    @field:NotBlank
    val description: String? = null,

    @field:NotBlank
    val barcode: String? = null,

    @field:Positive
    val salePrice: Int? = null,

    @field:Positive
    val originalPrice: Int? = null,

    @field:FutureOrPresent
    val expirationDate: LocalDate? = null,

    val category: ProductCategory? = null,

    val size: ProductSize? = null,
)