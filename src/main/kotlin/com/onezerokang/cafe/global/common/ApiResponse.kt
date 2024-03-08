package com.onezerokang.cafe.global.common

import com.onezerokang.cafe.global.error.ApiException
import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val meta: Meta,
    val data: T? = null,
) {
    companion object {
        fun success(status: HttpStatus): ApiResponse<Any> {
            return ApiResponse(
                meta = Meta.of(status),
                data = null,
            )
        }

        fun <T>success(status: HttpStatus, data: T): ApiResponse<T> {
            return ApiResponse(
                meta = Meta.of(status),
                data = data,
            )
        }

        fun error(e: ApiException): ApiResponse<Any> {
            return ApiResponse(
                meta = Meta.of(e),
                data = null,
            )
        }
    }
}

data class Meta(
    val code: Int,
    val message: String,
) {
    companion object {
        fun of(status: HttpStatus): Meta {
            return Meta(status.value(), status.reasonPhrase)
        }

        fun of(e: ApiException): Meta {
            return Meta(e.status.value(), e.message)
        }
    }
}