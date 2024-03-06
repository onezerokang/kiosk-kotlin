package com.onezerokang.cafe.auth.exception

import com.onezerokang.cafe.global.error.ApiException
import org.springframework.http.HttpStatus

class PhoneNumberAlreadyRegisteredException(
    override val message: String = "The phone number is already registered",
    override val status: HttpStatus = HttpStatus.CONFLICT,
) : ApiException() {
}