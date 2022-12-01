package com.letters.to.stamphistory.infra.querydsl

import com.letters.to.stamphistory.application.QStampHistoryResponse
import com.letters.to.stamphistory.application.StampHistoryExtendsRepository
import com.letters.to.stamphistory.application.StampHistoryResponse
import com.letters.to.stamphistory.domain.QStampHistory.stampHistory
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime

@Component
class StampHistoryQueryRepository(private val jpaQueryFactory: JPAQueryFactory) : StampHistoryExtendsRepository {
    override fun findAllByLastMonth(memberId: Long): List<StampHistoryResponse> {
        val now = LocalDate.now()
        val start = now.withDayOfMonth(1).atStartOfDay()
        val end = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX)

        return jpaQueryFactory.from(stampHistory)
            .where(
                stampHistory.createdDate.between(start, end),
                stampHistory.member.id.eq(memberId)
            )
            .select(
                QStampHistoryResponse(
                    stampHistory.type,
                    stampHistory.quantity,
                    stampHistory.createdDate,
                    stampHistory.id
                )
            )
            .orderBy(stampHistory.id.desc())
            .fetch()
    }
}
