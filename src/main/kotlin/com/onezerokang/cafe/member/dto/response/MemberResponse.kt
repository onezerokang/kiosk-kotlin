package com.onezerokang.cafe.member.dto.response

import com.onezerokang.cafe.member.domain.Member

data class MemberResponse(
    val id: Long,
    val phoneNumber: String,
) {
    companion object {
        fun of(member: Member): MemberResponse {
            return MemberResponse(
                id = member.id!!,
                phoneNumber = member.phoneNumber,
            )
        }
    }
}
