package com.onezerokang.cafe.global.error.exception

import com.onezerokang.cafe.global.error.ApiException
import org.springframework.http.HttpStatus

class InternalServerErrorException(
    override val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    override val message: String = "Internal server error",
) : ApiException()