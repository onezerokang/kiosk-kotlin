package com.onezerokang.cafe.auth.dto.request

import com.onezerokang.cafe.member.domain.Member
import org.springframework.security.crypto.password.PasswordEncoder

data class MemberSignupRequest(
    val phoneNumber: String,
    val password: String,
) {
    fun toEntity(passwordEncoder: PasswordEncoder): Member {
        return Member(
            phoneNumber = phoneNumber,
            password = passwordEncoder.encode(password),
        )
    }
}