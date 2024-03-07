package com.onezerokang.cafe.product.domain

import com.onezerokang.cafe.global.domain.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Product(
    val name: String,

    val description: String,

    val barcode: String,

    val salePrice: Int,

    val originalPrice: Int,

    val expirationDate: LocalDate,

    @Enumerated(EnumType.STRING)
    val category: ProductCategory,

    @Enumerated(EnumType.STRING)
    val size: ProductSize,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseEntity()