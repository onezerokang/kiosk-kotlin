package com.onezerokang.cafe.product.controller

import com.onezerokang.cafe.global.annotation.AuthMember
import com.onezerokang.cafe.global.common.ApiResponse
import com.onezerokang.cafe.product.dto.request.ProductCreateRequest
import com.onezerokang.cafe.product.dto.request.ProductUpdateRequest
import com.onezerokang.cafe.product.dto.response.ProductResponse
import com.onezerokang.cafe.product.service.ProductService
import jakarta.validation.Valid
import jakarta.websocket.server.PathParam
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
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

    @GetMapping
    fun getProductsByPage(
        @PathParam("lastId") lastId: Long,
        @PathParam("size") size: Int? = 10,
        @AuthMember memberId: Long,
    ): ResponseEntity<ApiResponse<Any>> {
        val productPages = productService.getProductsByPage(lastId = lastId, memberId = memberId, size = size!!)
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(status = HttpStatus.OK, data = productPages))
    }

    @GetMapping("/{productId}")
    fun getProduct(
        @PathVariable("productId") productId: Long,
        @AuthMember memberId: Long
    ): ResponseEntity<ApiResponse<ProductResponse>> {
        val response = productService.getProduct(productId = productId, memberId = memberId)
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(status = HttpStatus.OK, data = response))
    }

    @PatchMapping("/{productId}")
    fun updateProduct(
        @PathVariable("productId") productId: Long,
        @AuthMember memberId: Long,
        @Valid @RequestBody request: ProductUpdateRequest
    ):ResponseEntity<ApiResponse<Any>> {
        productService.updateProduct(request = request, productId = productId, memberId = memberId)
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(status = HttpStatus.OK))
    }

    @DeleteMapping("/{productId}")
    fun deleteProduct(
        @PathVariable("productId") productId: Long,
        @AuthMember memberId: Long
    ): ResponseEntity<ApiResponse<Any>> {
        productService.deleteProduct(productId = productId, memberId = memberId)
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(status = HttpStatus.OK))
    }
}