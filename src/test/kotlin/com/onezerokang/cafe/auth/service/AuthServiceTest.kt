package com.onezerokang.cafe.auth.service

import com.onezerokang.cafe.auth.dto.request.MemberLoginRequest
import com.onezerokang.cafe.auth.dto.request.MemberSignupRequest
import com.onezerokang.cafe.auth.exception.PhoneNumberAlreadyRegisteredException
import com.onezerokang.cafe.auth.exception.UnAuthenticationException
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
        val request = MemberSignupRequest(phoneNumber = "01012341234", password = "1234")
        val member = Member(phoneNumber = "01012341234", password = "1234")
        memberRepository.save(member)

        // when then
        assertThatThrownBy { authService.signup(request) }
            .isInstanceOf(PhoneNumberAlreadyRegisteredException::class.java)
            .hasMessage("The phone number is already registered")
    }

    @DisplayName("회원은 로그인 할 수 있다.")
    @Test
    fun login() {
        // given
        val request = MemberLoginRequest(phoneNumber = "01012341234", password = "1234")
        saveMember(phoneNumber = "01012341234", password = "1234")

        // when
        val response = authService.login(request)

        //then
        assertThat(response.accessToken).isNotBlank()
    }

    @DisplayName("등록되지 않은 휴대폰 번호로 로그인 시, 로그인에 실패한다.")
    @Test
    fun loginWithUnRegisteredPhoneNumber() {
        // given
        val request = MemberLoginRequest(phoneNumber = "01099999999", password = "1234")
        saveMember(phoneNumber = "01012341234", password = "1234")

        // when then
        assertThatThrownBy { authService.login(request) }
            .isInstanceOf(UnAuthenticationException::class.java)
            .hasMessage("Authentication failed")

    }

    @DisplayName("비밀번호가 일치하지 않을 시, 로그인에 실패한다.")
    @Test
    fun loginWithMissMatchedPassword() {
        // given
        val request = MemberLoginRequest(phoneNumber = "01012341234", password = "9999")
        saveMember(phoneNumber = "01012341234", password = "1234")

        // when then
        assertThatThrownBy { authService.login(request) }
            .isInstanceOf(UnAuthenticationException::class.java)
            .hasMessage("Authentication failed")
    }

    private fun saveMember(phoneNumber: String, password: String) {
        val member = Member(
            phoneNumber = phoneNumber,
            password = passwordEncoder.encode(password)
        )
        memberRepository.save(member)
    }
}