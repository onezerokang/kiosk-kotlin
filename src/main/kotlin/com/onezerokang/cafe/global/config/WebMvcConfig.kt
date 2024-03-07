package com.onezerokang.cafe.global.config

import com.onezerokang.cafe.global.interceptor.MemberAuthInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val memberAuthInterceptor: MemberAuthInterceptor,
): WebMvcConfigurer{
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(memberAuthInterceptor)
            .excludePathPatterns("/api/auth/signup", "/api/auth/login")
    }
}