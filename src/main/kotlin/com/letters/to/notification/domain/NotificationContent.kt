package com.letters.to.notification.domain

@JvmInline
value class NotificationContent(val value: String) {
    init {
        require(value.length <= 100)
    }
}
