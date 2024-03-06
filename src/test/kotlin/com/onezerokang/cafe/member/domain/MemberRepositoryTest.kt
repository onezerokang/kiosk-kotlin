package com.onezerokang.cafe.member.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
class MemberRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository,
) {
    @DisplayName("휴대폰 번호로 회원을 조회할 수 있다.")
    @Test
    fun findByPhoneNumber() {
        // given
        val phoneNumber = "01012341234"
        val password = "1234"
        memberRepository.save(Member(phoneNumber = phoneNumber, password = password))

        // when
        val member = memberRepository.findByPhoneNumber(phoneNumber = phoneNumber)

        //then
        assertThat(member?.phoneNumber).isEqualTo(phoneNumber)
        assertThat(member?.password).isEqualTo(password)
    }

    @DisplayName("등록되지 않은 휴대폰 번호로 회원을 조회하면 null을 반환한다.")
    @Test
    fun findByUnregisteredPhoneNumber() {
        // given
        val phoneNumber = "01012341234"

        // when
        val member = memberRepository.findByPhoneNumber(phoneNumber = phoneNumber)

        //then
        assertThat(member).isNull()
    }

    @DisplayName("휴대폰 번호가 등록되어 있는지 확인할 수 있다.")
    @Test
    fun existsByPhoneNumber() {
        // given
        val phoneNumber = "01012341234"
        val password = "1234"
        memberRepository.save(Member(phoneNumber = phoneNumber, password = password))

        // when
        val isExist1 = memberRepository.existsByPhoneNumber(phoneNumber)

        //then
        assertThat(isExist1).isTrue()
    }
}