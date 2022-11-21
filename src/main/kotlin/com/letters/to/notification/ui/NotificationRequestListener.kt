package com.letters.to.notification.ui

import com.letters.to.notification.application.NotifyService
import com.letters.to.notification.domain.NotificationType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
class NotificationRequestListener(private val notifyService: NotifyService) {

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun notify(request: NotificationRequest) {
        notifyService.notify(
            title = request.title,
            content = request.content,
            type = NotificationType.valueOf(request.type),
            intent = request.intent,
            memberId = request.memberId
        )
    }
}
