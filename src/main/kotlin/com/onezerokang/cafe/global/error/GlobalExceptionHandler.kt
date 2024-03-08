package com.onezerokang.cafe.global.error

import com.onezerokang.cafe.global.common.ApiResponse
import com.onezerokang.cafe.global.error.exception.InternalServerErrorException
import com.onezerokang.cafe.global.error.exception.BadRequestException
import com.onezerokang.cafe.global.error.exception.MethodNotAllowedException

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.lang.Exception

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Any>> {
        log.error("Exception: $e")
        val ex = InternalServerErrorException()
        return ResponseEntity.status(ex.status).body(ApiResponse.error(ex))
    }

    @ExceptionHandler(ApiException::class)
    fun handleApiException(e: ApiException): ResponseEntity<ApiResponse<Any>> {
        log.info("ApiException: $e")
        val response = ApiResponse.error(e)
        return ResponseEntity.status(e.status).body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Any>> {
        log.info("MethodArgumentNotValidException: $e")
        val message = e.bindingResult.fieldErrors.joinToString(",") { "${it.field} : ${it.defaultMessage}" }
        val ex = BadRequestException(message = message)
        return ResponseEntity.status(ex.status).body(ApiResponse.error(ex))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ApiResponse<Any>> {
        log.info("MethodArgumentTypeMismatchException: $e")
        val ex = BadRequestException()
        return ResponseEntity.status(ex.status).body(ApiResponse.error(ex))
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ApiResponse<Any>> {
        log.info("HttpRequestMethodNotSupportedException: $e")
        val ex = MethodNotAllowedException()
        return ResponseEntity.status(ex.status).body(ApiResponse.error(ex))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Any>> {
        log.info("HttpMessageNotReadableException: ${e.message}")
        val ex = BadRequestException()
        return ResponseEntity.status(ex.status).body(ApiResponse.error(ex))
    }
}