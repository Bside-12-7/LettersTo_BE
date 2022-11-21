package com.letters.to.notification.ui

data class NotificationRequest(
    val title: String,
    val content: String,
    val type: String,
    val intent: String,
    val memberId: Long
)
