package com.letters.to.notification.application

import com.letters.to.notification.domain.Notification

interface NotificationExtendsRepository {
    fun findMoreBy(memberId: Long, cursor: Long?, read: Boolean?): List<Notification>
}
