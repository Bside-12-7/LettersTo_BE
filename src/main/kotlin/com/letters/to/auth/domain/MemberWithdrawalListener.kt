package com.letters.to.auth.domain

import com.letters.to.member.application.MemberWithdrawalEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MemberWithdrawalListener(private val authenticationRepository: AuthenticationRepository) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun listen(event: MemberWithdrawalEvent) {
        authenticationRepository.deleteByMemberId(event.id)
    }
}
