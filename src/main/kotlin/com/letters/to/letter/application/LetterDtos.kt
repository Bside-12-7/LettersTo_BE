package com.letters.to.letter.application

import com.letters.to.letter.domain.DeliveryType
import com.letters.to.letter.domain.PaperColor
import com.letters.to.letter.domain.PaperType
import java.time.LocalDateTime
import javax.validation.constraints.Size

data class PublicLetterWriteRequest(
    val title: String,
    val content: String,
    val paperType: PaperType,
    val paperColor: PaperColor,
    val stampId: Long,
    val topics: List<Long>,
    val personalities: List<Long>,

    @field:Size(max = 5)
    val files: List<String>
)

data class PublicLetterResponse(
    val id: Long,
    val title: String,
    val fromAddress: String,
    val fromNickname: String,
    val topics: List<String>,
    val personalities: List<String>,
    val paperColor: PaperColor,
    val stampId: Long,
    val createdDate: LocalDateTime
)

data class LetterDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val fromAddress: String,
    val fromNickname: String,
    val paperColor: PaperColor,
    val paperType: PaperType,
    val stampId: Long,
    val replied: Boolean,
    val files: List<String>,
    val createdDate: LocalDateTime
)

data class PublicLetterMoreResponse(
    val content: List<PublicLetterResponse>,
    val cursor: Long?
)

data class DeliveryLetterWriteRequest(
    val id: Long,
    val title: String,
    val content: String,
    val paperType: PaperType,
    val paperColor: PaperColor,
    val stampId: Long,

    @field:Size(max = 3)
    val files: List<String>,

    val deliveryType: DeliveryType
)

data class DeliveryLetterMoreResponse(
    val content: List<DeliveryLetterResponse>,
    val cursor: Long?
)

data class DeliveryLetterResponse(
    val id: Long,
    val title: String,
    val fromAddress: String,
    val fromNickname: String,
    val toAddress: String,
    val toNickname: String,
    val paperColor: PaperColor,
    val stampId: Long,
    val read: Boolean,
    val me: Boolean,
    val deliveryType: DeliveryType,
    val deliveryDate: LocalDateTime,
)

data class LetterBoxResponse(
    val fromMemberId: Long,
    val fromMemberNickname: String,
    val new: Boolean
)

data class LetterBoxDetailResponse(
    val fromNickname: String,
    val fromAddress: String,
    val startDate: LocalDateTime,
    val topics: List<String>,
    val personalities: List<String>
)
