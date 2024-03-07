package com.onezerokang.cafe.product.domain

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository:JpaRepository<Product, Long> {
    fun existsByBarcode(barcode: String): Boolean

    fun findByIdAndMemberId(productId: Long, memberId: Long): Product?
}