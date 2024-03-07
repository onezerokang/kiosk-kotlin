package com.onezerokang.cafe.product.exception

import com.onezerokang.cafe.global.error.ApiException
import org.springframework.http.HttpStatus

class BarcodeAlreadyRegisteredException(
    override val message: String = "The barcode is already registered.",
    override val status: HttpStatus = HttpStatus.CONFLICT,
) : ApiException() {
}