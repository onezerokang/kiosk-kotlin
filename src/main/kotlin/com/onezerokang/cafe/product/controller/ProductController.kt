package com.onezerokang.cafe.product.controller

import com.onezerokang.cafe.global.annotation.AuthMember
import com.onezerokang.cafe.global.common.ApiResponse
import com.onezerokang.cafe.product.dto.request.ProductCreateRequest
import com.onezerokang.cafe.product.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/products")
@RestController
class ProductController(
    private val productService: ProductService,
) {
    @PostMapping
    fun createProduct(
        @Valid @RequestBody request: ProductCreateRequest,
        @AuthMember memberId: Long,
    ): ResponseEntity<ApiResponse<Any>> {
        productService.createProduct(request, memberId)
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(status = HttpStatus.CREATED))
    }
}