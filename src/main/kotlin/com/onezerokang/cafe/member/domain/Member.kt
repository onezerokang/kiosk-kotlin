package com.onezerokang.cafe.member.domain

import com.onezerokang.cafe.auth.dto.request.MemberSignupRequest
import com.onezerokang.cafe.global.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member(
    val phoneNumber: String,
    val password: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
): BaseEntity() {
    companion object {
        fun create(request: MemberSignupRequest) {
            Member(phoneNumber = request.phoneNumber, password = request.password)
        }
    }
}