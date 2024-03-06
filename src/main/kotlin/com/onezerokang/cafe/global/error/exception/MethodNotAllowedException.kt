package com.onezerokang.cafe.global.error.exception

import com.onezerokang.cafe.global.error.ApiException
import org.springframework.http.HttpStatus

class MethodNotAllowedException(
    override val message: String = "Method not allowed",
    override val status: HttpStatus = HttpStatus.METHOD_NOT_ALLOWED,
) : ApiException()