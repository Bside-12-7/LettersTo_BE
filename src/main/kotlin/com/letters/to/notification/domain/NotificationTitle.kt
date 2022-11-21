package com.letters.to.notification.domain

@JvmInline
value class NotificationTitle(val value: String) {
    init {
        require(value.length <= 50)
    }
}
