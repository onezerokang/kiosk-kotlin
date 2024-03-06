package com.onezerokang.cafe.member.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository:JpaRepository<Member, Long> {
    fun findByPhoneNumber(phoneNumber: String): Member?

    fun existsByPhoneNumber(phoneNumber: String): Boolean
}