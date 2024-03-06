package com.onezerokang.cafe.auth.service

import com.onezerokang.cafe.auth.dto.request.MemberSignupRequest
import com.onezerokang.cafe.auth.exception.PhoneNumberAlreadyRegisteredException
import com.onezerokang.cafe.member.domain.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
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
}