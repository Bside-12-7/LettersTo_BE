package com.letters.to.event

import com.letters.to.member.application.MemberRegisterEvent
import com.letters.to.member.domain.MemberRepository
import com.letters.to.member.domain.findByIdAndActive
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MemberRegisterListener(private val memberRepository: MemberRepository) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun listen(event: MemberRegisterEvent) {
        val member = memberRepository.findByIdAndActive(event.id)

        member?.giveStamp(100)
    }
}
