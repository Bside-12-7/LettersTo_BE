package com.letters.to.letter.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeliveryLetterOpenService(
    private val deliveryLetterQueryRepository: DeliveryLetterQueryRepository
) {
    fun open(memberId: Long, id: Long): LetterDetailResponse {
        val deliveryLetter = deliveryLetterQueryRepository.findOneBy(memberId, id)
            ?: throw NoSuchElementException("유효한 편지가 아닙니다")

        if (memberId == deliveryLetter.toMember.id) {
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
            canReply = !deliveryLetter.replied && deliveryLetter.fromMember.id != memberId,
            files = deliveryLetter.pictures.map { it.fileId },
            createdDate = deliveryLetter.createdDate
        )
    }
}
