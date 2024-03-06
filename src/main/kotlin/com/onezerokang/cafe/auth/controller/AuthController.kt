package com.onezerokang.cafe.auth.controller

import com.onezerokang.cafe.auth.dto.request.MemberSignupRequest
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
    fun test(@Valid @RequestBody request: MemberSignupRequest): ResponseEntity<ApiResponse<Any>> {
        authService.signup(request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED))
    }
}