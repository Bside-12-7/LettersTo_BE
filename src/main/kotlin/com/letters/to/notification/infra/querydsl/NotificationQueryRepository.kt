package com.letters.to.notification.infra.querydsl

import com.letters.to.notification.application.NotificationExtendsRepository
import com.letters.to.notification.domain.Notification
import com.letters.to.notification.domain.QNotification.notification
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class NotificationQueryRepository(private val jpaQueryFactory: JPAQueryFactory) : NotificationExtendsRepository {
    override fun findMoreBy(memberId: Long, cursor: Long?, read: Boolean?): List<Notification> {
        return jpaQueryFactory.selectFrom(notification)
            .where(
                cursor?.let { notification.id.lt(it) },
                read?.let { notification.read.eq(it) }
            )
            .orderBy(notification.id.desc())
            .limit(50)
            .fetch()
    }
}
