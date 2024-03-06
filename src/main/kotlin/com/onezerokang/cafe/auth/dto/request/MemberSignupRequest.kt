package com.onezerokang.cafe.auth.dto.request

import com.onezerokang.cafe.member.domain.Member
import jakarta.validation.constraints.NotBlank
import org.springframework.security.crypto.password.PasswordEncoder

data class MemberSignupRequest(
    @field:NotBlank
    val phoneNumber: String,

    @field:NotBlank
    val password: String,
) {
    fun toEntity(passwordEncoder: PasswordEncoder): Member {
        return Member(
            phoneNumber = phoneNumber,
            password = passwordEncoder.encode(password),
        )
    }
}