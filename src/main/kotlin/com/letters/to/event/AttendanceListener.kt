package com.letters.to.event

import com.letters.to.attendance.application.AttendanceEvent
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
class AttendanceListener(
    private val memberRepository: MemberRepository,
    private val stampHistoryRepository: StampHistoryRepository,
    private val notifyClient: NotifyClient
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun listen(event: AttendanceEvent) {
        val member = memberRepository.findByIdAndActive(event.memberId) ?: return

        member.giveStamp(5)

        stampHistoryRepository.save(
            StampHistory(
                member = member,
                type = StampHistoryType.DAILY,
                quantity = 5
            )
        )

        notifyClient.notify(
            title = "앱 방문 우표 5개 지급!",
            content = "오늘도 찾아와주셨군요!\n우표 5개 지급되었습니다!",
            type = "STAMP",
            intent = "{}",
            memberId = member.id
        )
    }
}
