package com.onezerokang.cafe.auth.controller

import com.onezerokang.cafe.auth.dto.request.MemberLoginRequest
import com.onezerokang.cafe.auth.dto.request.MemberSignupRequest
import com.onezerokang.cafe.auth.dto.response.MemberLoginResponse
import com.onezerokang.cafe.auth.service.AuthService
import com.onezerokang.cafe.global.common.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/auth")
@RestController
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/signup")
    fun signup(@Valid @RequestBody request: MemberSignupRequest): ResponseEntity<ApiResponse<Any>> {
        authService.signup(request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: MemberLoginRequest): ResponseEntity<ApiResponse<MemberLoginResponse>> {
        val response = authService.login(request = request)
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, response))
    }
}