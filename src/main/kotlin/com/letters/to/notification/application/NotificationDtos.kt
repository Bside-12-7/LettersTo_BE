package com.letters.to.notification.application

import com.letters.to.notification.domain.Notification
import com.letters.to.notification.domain.NotificationType

data class NotificationMoreResponse(
    val content: List<NotificationResponse>,
    val cursor: Long?
)

data class NotificationResponse(
    val id: Long,
    val title: String,
    val content: String,
    val type: NotificationType,
    val intent: String,
    val read: Boolean
) {
    constructor(notification: Notification) : this(
        id = notification.id,
        title = notification.title.value,
        content = notification.content.value,
        type = notification.type,
        intent = notification.intent,
        read = notification.read
    )
}

data class NotificationMoreRequest(
    val read: Boolean?,
    val cursor: Long?
)
