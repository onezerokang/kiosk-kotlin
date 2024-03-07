package com.onezerokang.cafe.auth.service

import com.onezerokang.cafe.auth.dto.request.MemberLoginRequest
import com.onezerokang.cafe.auth.dto.request.MemberSignupRequest
import com.onezerokang.cafe.auth.dto.response.MemberLoginResponse
import com.onezerokang.cafe.auth.exception.PhoneNumberAlreadyRegisteredException
import com.onezerokang.cafe.auth.exception.UnAuthenticationException
import com.onezerokang.cafe.auth.util.AuthTokenGenerator
import com.onezerokang.cafe.member.domain.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authTokenGenerator: AuthTokenGenerator,
) {
    @Transactional
    fun signup(request: MemberSignupRequest) {
        val isExist = memberRepository.existsByPhoneNumber(request.phoneNumber)
        if (isExist) {
            throw PhoneNumberAlreadyRegisteredException()
        }

        val member = request.toEntity(passwordEncoder)
        memberRepository.save(member)
    }

    @Transactional
    fun login(request: MemberLoginRequest): MemberLoginResponse {
        val member = memberRepository.findByPhoneNumber(phoneNumber = request.phoneNumber)
            ?: throw UnAuthenticationException()

        val isMatched = passwordEncoder.matches(request.password, member.password)
        if (!isMatched) {
            throw UnAuthenticationException()
        }

        return authTokenGenerator.generate(member.id!!)
    }
 }