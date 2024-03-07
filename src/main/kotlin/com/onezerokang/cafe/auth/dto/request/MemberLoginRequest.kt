package com.onezerokang.cafe.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class MemberLoginRequest(
    @field:NotBlank
    val phoneNumber: String,

    @field:NotBlank
    val password: String,
)
