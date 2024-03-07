package com.onezerokang.cafe.member.exception

import com.onezerokang.cafe.global.error.ApiException
import org.springframework.http.HttpStatus

class MemberNotFoundException(
    override val message: String = "Member not found",
    override val status: HttpStatus = HttpStatus.NOT_FOUND,
) : ApiException() {
}