package com.letters.to.event

import com.letters.to.notification.ui.NotificationRequest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class NotifyClient(private val applicationEventPublisher: ApplicationEventPublisher) {

    fun notify(title: String, content: String, type: String, intent: String, memberId: Long) {
        applicationEventPublisher.publishEvent(
            NotificationRequest(
                title = title,
                content = content,
                type = type,
                intent = intent,
                memberId = memberId
            )
        )
    }
}
