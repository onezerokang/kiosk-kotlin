package com.onezerokang.cafe.global.interceptor

import com.onezerokang.cafe.auth.exception.UnAuthenticationException
import com.onezerokang.cafe.auth.util.JwtUtil
import com.onezerokang.cafe.member.domain.MemberRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class MemberAuthInterceptor(
    private val memberRepository: MemberRepository,
    private val jwtUtil: JwtUtil,
): HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val auth = request.getHeader(HttpHeaders.AUTHORIZATION) ?: throw UnAuthenticationException()

        if (!auth.startsWith("Bearer")) {
            throw UnAuthenticationException()
        }

        val accessToken = auth.split(" ")[1].trim()
        if(!jwtUtil.validateToken(accessToken)) {
            throw UnAuthenticationException()
        }

        val memberId = jwtUtil.extractSubject(accessToken)
        val isExist = memberRepository.existsById(memberId.toLong())
        if (!isExist) {
            throw UnAuthenticationException()
        }

        return true;
    }
}