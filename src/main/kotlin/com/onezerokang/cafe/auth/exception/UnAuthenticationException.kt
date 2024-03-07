package com.onezerokang.cafe.auth.exception

import com.onezerokang.cafe.global.error.ApiException
import org.springframework.http.HttpStatus

class UnAuthenticationException(
    override val message: String = "Authentication failed",
    override val status: HttpStatus = HttpStatus.UNAUTHORIZED
) : ApiException()