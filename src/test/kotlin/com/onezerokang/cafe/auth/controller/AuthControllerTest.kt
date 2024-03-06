package com.onezerokang.cafe.auth.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.onezerokang.cafe.auth.dto.request.MemberSignupRequest
import com.onezerokang.cafe.auth.service.AuthService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [AuthController::class])
@AutoConfigureMockMvc
class AuthControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockBean private val authService: AuthService,
) {

    @DisplayName("회원가입을 할 수 있다.")
    @Test
    fun signup() {
        val url = "/api/auth/signup"
        val request = MemberSignupRequest(phoneNumber = "01012341234", password = "1234")

        mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.meta.code").value(201))
            .andExpect(jsonPath("$.meta.message").value("Created"))
            .andExpect(jsonPath("$.data").value(null))
    }

    @DisplayName("회원가입 시, 전화번호는 필수다.")
    @Test
    fun signupWithBlankPhoneNumber() {
        val url = "/api/auth/signup"
        val request = MemberSignupRequest(phoneNumber = "", password = "1234")

        mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.meta.code").value(400))
            .andExpect(jsonPath("$.meta.message").value("Bad Request"))
            .andExpect(jsonPath("$.data").value(null))
    }

    @DisplayName("회원가입 시, 비밀번호는 필수다.")
    @Test
    fun signupWithBlankPassword() {
        val url = "/api/auth/signup"
        val request = MemberSignupRequest(phoneNumber = "01012341234", password = "")

        mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.meta.code").value(400))
            .andExpect(jsonPath("$.meta.message").value("Bad Request"))
            .andExpect(jsonPath("$.data").value(null))
    }
}