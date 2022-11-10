package com.letters.to.letter.application

import com.letters.to.letter.domain.DeliveryLetter

interface DeliveryLetterExtendsRepository {
    fun findMoreBy(cursor: Long?, fromMemberId: Long, toMemberId: Long): List<DeliveryLetterResponse>
    fun findOneBy(memberId: Long, id: Long): DeliveryLetter?
    fun findFromMemberIdByNewDeliveredLetter(toMemberId: Long): List<Long>
}
