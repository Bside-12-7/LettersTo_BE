package com.letters.to.letter.application

import com.letters.to.auth.domain.AccessTokenPayload
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class DeliveryLetterQueryService(
    private val deliveryLetterQueryRepository: DeliveryLetterQueryRepository
) {
    fun findMoreBy(
        accessTokenPayload: AccessTokenPayload,
        fromMemberId: Long,
        cursor: Long?
    ): DeliveryLetterMoreResponse {
        val deliveryLetters =
            deliveryLetterQueryRepository.findMoreBy(cursor, fromMemberId, accessTokenPayload.memberId)

        return DeliveryLetterMoreResponse(
            content = deliveryLetters,
            cursor = deliveryLetters.lastOrNull()?.id ?: cursor
        )
    }
}
