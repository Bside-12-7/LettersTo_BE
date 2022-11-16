package com.letters.to.letter.application

import com.letters.to.auth.domain.AccessTokenPayload
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeliveryLetterOpenService(
    private val deliveryLetterQueryRepository: DeliveryLetterQueryRepository
) {
    fun open(accessTokenPayload: AccessTokenPayload, id: Long): LetterDetailResponse {
        val deliveryLetter = deliveryLetterQueryRepository.findOneBy(accessTokenPayload.memberId, id)
            ?: throw NoSuchElementException("유효한 편지가 아닙니다")

        if (accessTokenPayload.memberId == deliveryLetter.toMember.id) {
            deliveryLetter.open()
        }

        return LetterDetailResponse(
            id = deliveryLetter.id,
            title = deliveryLetter.title.value,
            content = deliveryLetter.content.value,
            fromAddress = deliveryLetter.fromGeolocation.fullname,
            fromNickname = deliveryLetter.fromMember.nickname.value,
            paperColor = deliveryLetter.paperColor,
            paperType = deliveryLetter.paperType,
            alignType = deliveryLetter.alignType,
            stampId = deliveryLetter.stamp.id,
            replied = deliveryLetter.replied,
            files = deliveryLetter.pictures.map { it.fileId },
            createdDate = deliveryLetter.createdDate
        )
    }
}
