package com.letters.to.notification.application

import com.letters.to.notification.domain.Notification
import com.letters.to.notification.domain.NotificationContent
import com.letters.to.notification.domain.NotificationRepository
import com.letters.to.notification.domain.NotificationTitle
import com.letters.to.notification.domain.NotificationType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NotifyService(
    private val notificationRepository: NotificationRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun notify(title: String, content: String, type: NotificationType, intent: String, memberId: Long) {
        val notification = Notification(
            title = NotificationTitle(title),
            content = NotificationContent(content),
            type = type,
            memberId = memberId,
            intent = intent
        )

        notificationRepository.save(notification)

        applicationEventPublisher.publishEvent(NotifyEvent(id = notification.id))
    }
}
