package com.onezerokang.cafe.global.config

import com.onezerokang.cafe.global.interceptor.MemberAuthInterceptor
import com.onezerokang.cafe.global.resolver.MemberAuthArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val memberAuthInterceptor: MemberAuthInterceptor,
    private val memberAuthArgumentResolver: MemberAuthArgumentResolver,
): WebMvcConfigurer{
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(memberAuthInterceptor)
            .excludePathPatterns("/api/auth/signup", "/api/auth/login")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(memberAuthArgumentResolver)
    }
}