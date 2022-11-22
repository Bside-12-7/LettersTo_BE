package com.letters.to.event

import com.letters.to.member.application.MemberRegisterEvent
import com.letters.to.member.domain.MemberRepository
import com.letters.to.member.domain.findByIdAndActive
import com.letters.to.stamphistory.domain.StampHistory
import com.letters.to.stamphistory.domain.StampHistoryRepository
import com.letters.to.stamphistory.domain.StampHistoryType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MemberRegisterListener(
    private val memberRepository: MemberRepository,
    private val stampHistoryRepository: StampHistoryRepository,
    private val notifyClient: NotifyClient
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun listen(event: MemberRegisterEvent) {
        val member = memberRepository.findByIdAndActive(event.id) ?: return

        member.giveStamp(100)

        stampHistoryRepository.save(
            StampHistory(
                member = member,
                type = StampHistoryType.REGISTRATION,
                quantity = 100
            )
        )

        notifyClient.notify(
            title = "최초 가입 기념 우표 100개 지급!",
            content = "최초 가입 기념 우표가 지급되었습니다.\n사람들과 이야기 나누러 가볼까요?",
            type = "STAMP",
            intent = "{}",
            memberId = member.id
        )
    }
}
