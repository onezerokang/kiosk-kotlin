package com.onezerokang.cafe.global.resolver

import com.onezerokang.cafe.auth.exception.UnAuthenticationException
import com.onezerokang.cafe.auth.util.JwtUtil
import com.onezerokang.cafe.global.annotation.AuthMember
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class MemberAuthArgumentResolver(
    private val jwtUtil: JwtUtil,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthMember::class.java) &&
                parameter.parameterType == Long::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Long {
        val auth = webRequest.getHeader(HttpHeaders.AUTHORIZATION)
            ?: throw UnAuthenticationException()

        if (!auth.startsWith("Bearer")) {
            throw UnAuthenticationException()
        }

        val accessToken = auth.split(" ")[1].trim()
        if (!jwtUtil.validateToken(accessToken)) {
            throw UnAuthenticationException()
        }

        return jwtUtil.extractSubject(accessToken).toLong()
    }
}