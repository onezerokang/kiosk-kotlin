package com.onezerokang.cafe.auth.util

import com.onezerokang.cafe.auth.dto.response.MemberLoginResponse
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthTokenGenerator(
    private val jwtUtil: JwtUtil
) {
    companion object {
        private const val ONE_DAY = 1000L * 60L * 60L * 24L
    }

    fun generate(memberId: Long): MemberLoginResponse {
        val now = Date().time
        val accessToken = jwtUtil.generateToken(memberId.toString(), Date(now + ONE_DAY))
        return MemberLoginResponse(accessToken = accessToken)
    }

}