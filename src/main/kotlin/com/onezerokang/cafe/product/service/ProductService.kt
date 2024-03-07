package com.onezerokang.cafe.product.service

import com.onezerokang.cafe.member.domain.MemberRepository
import com.onezerokang.cafe.member.exception.MemberNotFoundException
import com.onezerokang.cafe.product.domain.ProductRepository
import com.onezerokang.cafe.product.dto.request.ProductCreateRequest
import com.onezerokang.cafe.product.exception.BarcodeAlreadyRegisteredException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
) {
    fun createProduct(request: ProductCreateRequest, memberId: Long) {
        val isExist = productRepository.existsByBarcode(request.barcode)
        if (isExist) {
            throw BarcodeAlreadyRegisteredException()
        }

        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw MemberNotFoundException()


        val product = request.toEntity(member = member)
        productRepository.save(product)
    }
}