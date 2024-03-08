package com.onezerokang.cafe.product.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.onezerokang.cafe.auth.util.JwtUtil
import com.onezerokang.cafe.member.domain.MemberRepository
import com.onezerokang.cafe.product.domain.ProductCategory
import com.onezerokang.cafe.product.domain.ProductSize
import com.onezerokang.cafe.product.dto.request.ProductCreateRequest
import com.onezerokang.cafe.product.dto.request.ProductUpdateRequest
import com.onezerokang.cafe.product.service.ProductService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WebMvcTest(controllers = [ProductController::class])
class ProductControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockBean private val productService: ProductService,
    @MockBean private val memberRepository: MemberRepository,
    @MockBean private val jwtUtil: JwtUtil,
) {

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    fun createProduct() {
        // given
        val url = "/api/products"
        val request = ProductCreateRequest(
            name = "아메리카노",
            description = "아이스",
            barcode = "1234123412341",
            salePrice = 3500,
            originalPrice = 4000,
            expirationDate = LocalDate.of(2024, 12, 31),
            category = ProductCategory.DRINK,
            size = ProductSize.LARGE,
        )

        given(jwtUtil.validateToken(anyString())).willReturn(true)
        given(jwtUtil.extractSubject(anyString())).willReturn("1")
        given(memberRepository.existsById(anyLong())).willReturn(true)

        // when then
        mockMvc.perform(
            post(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.meta.code").value(201))
            .andExpect(jsonPath("$.meta.message").value("Created"))
            .andExpect(jsonPath("$.data").value(null))
    }

    @DisplayName("상품 상세 내역을 조회할 수 있다.")
    @Test
    fun getProduct() {
        // given
        val url = "/api/products/1"
        given(jwtUtil.validateToken(anyString())).willReturn(true)
        given(jwtUtil.extractSubject(anyString())).willReturn("1")
        given(memberRepository.existsById(anyLong())).willReturn(true)

        // when then
        mockMvc.perform(
            get(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.meta.code").value(200))
            .andExpect(jsonPath("$.meta.message").value("OK"))
            .andExpect(jsonPath("$.data").value(null))
    }

    @DisplayName("상품 속성을 수정할 수 있다.")
    @Test
    fun updateProduct() {
        // given
        val url = "/api/products/1"
        val request = ProductUpdateRequest(name = "소금빵(기한 한정 세일)", salePrice = 3000)

        given(jwtUtil.validateToken(anyString())).willReturn(true)
        given(jwtUtil.extractSubject(anyString())).willReturn("1")
        given(memberRepository.existsById(anyLong())).willReturn(true)

        // when then
        mockMvc.perform(
            patch(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.meta.code").value(200))
            .andExpect(jsonPath("$.meta.message").value("OK"))
            .andExpect(jsonPath("$.data").value(null))
    }

    @DisplayName("상품을 삭제할 수 있다.")
    @Test
    fun deleteProduct() {
        // given
        val url = "/api/products/1"
        given(jwtUtil.validateToken(anyString())).willReturn(true)
        given(jwtUtil.extractSubject(anyString())).willReturn("1")
        given(memberRepository.existsById(anyLong())).willReturn(true)

        // when then
        mockMvc.perform(
            delete(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.meta.code").value(200))
            .andExpect(jsonPath("$.meta.message").value("OK"))
            .andExpect(jsonPath("$.data").value(null))
    }

    @DisplayName("상품 목록을 조회할 수 있다.")
    @Test
    fun gerProductsByPage() {
        // given
        val url = "/api/products"
        given(jwtUtil.validateToken(anyString())).willReturn(true)
        given(jwtUtil.extractSubject(anyString())).willReturn("1")
        given(memberRepository.existsById(anyLong())).willReturn(true)

        // when then
        mockMvc.perform(
            get(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                .queryParam("lastId", "1")
                .queryParam("size", "10")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.meta.code").value(200))
            .andExpect(jsonPath("$.meta.message").value("OK"))
            .andExpect(jsonPath("$.data").isEmpty())
    }

}