package com.onezerokang.cafe.product.exception

import com.onezerokang.cafe.global.error.ApiException
import org.springframework.http.HttpStatus

class ProductNotFoundException(
    override val message: String = "Product not found",
    override val status: HttpStatus = HttpStatus.NOT_FOUND,
) : ApiException() {
}