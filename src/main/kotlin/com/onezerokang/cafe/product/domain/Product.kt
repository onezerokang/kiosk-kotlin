package com.onezerokang.cafe.product.domain

import com.onezerokang.cafe.global.domain.BaseEntity
import com.onezerokang.cafe.global.error.exception.BadRequestException
import com.onezerokang.cafe.global.util.KoreanInitialExtractor
import com.onezerokang.cafe.member.domain.Member
import com.onezerokang.cafe.product.dto.request.ProductUpdateRequest
import jakarta.persistence.*
import org.hibernate.annotations.*
import java.time.LocalDate

@SQLRestriction("status NOT IN ('DELETED')")
@Entity
class Product(
    var name: String,

    var initialName: String,

    var description: String,

    var barcode: String,

    var salePrice: Int,

    var originalPrice: Int,

    var expirationDate: LocalDate,

    @Enumerated(EnumType.STRING)
    var category: ProductCategory,

    @Enumerated(EnumType.STRING)
    var size: ProductSize,

    @Enumerated(EnumType.STRING)
    var status: ProductStatus = ProductStatus.SELLING,

    @JoinColumn(name = "member_id")
    @ManyToOne
    val member: Member,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseEntity() {

    fun update(request: ProductUpdateRequest) {
        request.name?.let {
            if (it.isBlank()) {
                throw BadRequestException(message = "name must not be blank")
            }
            name = it
            initialName = KoreanInitialExtractor.extract(it)
        }
        request.description?.let {
            if (it.isBlank()) {
                throw BadRequestException(message = "description must not be blank")
            }
            description = it
        }
        request.barcode?.let {
            if (it.isBlank()) {
                throw BadRequestException(message = "barcode must not be blank")
            }
            barcode = it
        }
        request.salePrice?.let {
            if (it <= 0) {
                throw BadRequestException(message = "salePrice must be positive")
            }
            salePrice = it
        }
        request.originalPrice?.let {
            if (it <= 0) {
                throw BadRequestException(message = "originalPrice must be positive")
            }
            originalPrice = it
        }
        request.expirationDate?.let {
            if (it.isBefore(LocalDate.now())) {
                throw BadRequestException(message = "expirationDate must be present or future date")
            }
        }
        request.category?.let {
            category = it
        }
        request.size?.let {
            size = it
        }
    }

    fun delete() {
        status = ProductStatus.DELETED
    }
}