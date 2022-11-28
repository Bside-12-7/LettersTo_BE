package com.letters.to.letter.application

import com.letters.to.letter.domain.DeliveryType
import com.letters.to.letter.domain.LetterRepository
import com.letters.to.member.domain.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class DeliveryDateService(
    private val memberRepository: MemberRepository,
    private val letterRepository: LetterRepository
) {
    fun deliveryDate(memberId: Long, letterId: Long, deliveryType: DeliveryType): DeliveryDateResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw NoSuchElementException("유효한 회원이 아닙니다.")
        val letter = letterRepository.findByIdOrNull(letterId) ?: throw NoSuchElementException("유효한 편지가 아닙니다.")

        return DeliveryDateResponse(
            deliveryType = deliveryType,
            deliveryDate = deliveryType.deliveryDate(letter.fromMember, member)
        )
    }
}
