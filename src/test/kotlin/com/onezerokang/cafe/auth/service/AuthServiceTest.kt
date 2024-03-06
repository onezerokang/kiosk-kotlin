package com.onezerokang.cafe.auth.service

import com.onezerokang.cafe.auth.dto.request.MemberSignupRequest
import com.onezerokang.cafe.auth.exception.PhoneNumberAlreadyRegisteredException
import com.onezerokang.cafe.member.domain.Member
import com.onezerokang.cafe.member.domain.MemberRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class AuthServiceTest @Autowired constructor(
    private val authService: AuthService,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @DisplayName("회원가입에 성공할 경우, 비밀번호는 암호화 된다.")
    @Test
    fun signup() {
        // given
        val phoneNumber = "01012341234"
        val password = "1234"
        val request = MemberSignupRequest(phoneNumber = phoneNumber, password = password)

        // when
        val signupResponse = authService.signup(request)

        //then
        val member = memberRepository.findByPhoneNumber(phoneNumber = phoneNumber)
        assertThat(member?.phoneNumber).isEqualTo(phoneNumber)
        assertThat(passwordEncoder.matches(password, member?.password)).isTrue()
    }

    @DisplayName("이미 등록된 휴대전화 번호일 경우, 회원가입에 실패한다.")
    @Test
    fun signupWithAlreadyRegisteredPhoneNumber() {
        // given
        val request = MemberSignupRequest(phoneNumber = "이상혁", password = "1234")
        val member = Member(phoneNumber = "이상혁", password = "1234")
        memberRepository.save(member)

        // when then
        assertThatThrownBy { authService.signup(request) }
            .isInstanceOf(PhoneNumberAlreadyRegisteredException::class.java)
            .hasMessage("The phone number is already registered")
    }
}