package com.letters.to.auth.domain

import com.letters.to.member.application.MemberWithdrawalEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MemberWithdrawalListener(private val authenticationRepository: AuthenticationRepository) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun listen(event: MemberWithdrawalEvent) {
        authenticationRepository.deleteByMemberId(event.id)
    }
}
