package com.onezerokang.cafe.global.error.exception

import com.onezerokang.cafe.global.error.ApiException
import org.springframework.http.HttpStatus

class BadRequestException(
    override val status: HttpStatus = HttpStatus.BAD_REQUEST,
    override val message: String = "Bad Request",
) : ApiException()