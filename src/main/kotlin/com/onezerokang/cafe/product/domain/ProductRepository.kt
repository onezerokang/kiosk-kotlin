package com.onezerokang.cafe.product.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductRepository:JpaRepository<Product, Long> {
    fun existsByBarcode(barcode: String): Boolean

    fun findByIdAndMemberId(productId: Long, memberId: Long): Product?

    @Query("SELECT p.* FROM product p", nativeQuery = true)
    fun findAllIncludingDeleted(): List<Product>
}