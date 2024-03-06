package com.onezerokang.cafe.global.error

import org.springframework.http.HttpStatus

abstract class ApiException: RuntimeException() {
    abstract override val message: String
    abstract val status: HttpStatus
}