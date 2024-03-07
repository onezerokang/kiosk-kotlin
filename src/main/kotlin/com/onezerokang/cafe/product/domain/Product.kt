package com.onezerokang.cafe.product.domain

import com.onezerokang.cafe.global.domain.BaseEntity
import com.onezerokang.cafe.member.domain.Member
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Product(
    val name: String,

    val initialName: String,

    val description: String,

    val barcode: String,

    val salePrice: Int,

    val originalPrice: Int,

    val expirationDate: LocalDate,

    @Enumerated(EnumType.STRING)
    val category: ProductCategory,

    @Enumerated(EnumType.STRING)
    val size: ProductSize,

    @JoinColumn(name = "member_id")
    @ManyToOne
    val member: Member,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseEntity()