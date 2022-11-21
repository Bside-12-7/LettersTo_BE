package com.letters.to.notification.domain

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "notification")
class Notification(
    @Column(name = "title", length = 50)
    val title: NotificationTitle,

    @Column(name = "content", length = 100)
    val content: NotificationContent,

    @Column(name = "`read`")
    var read: Boolean = false,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    val type: NotificationType,

    @Column(name = "intent")
    val intent: String,

    @Column
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
