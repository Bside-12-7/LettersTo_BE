package com.letters.to.topic.domain

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "topic")
@Entity
class Topic(
    @Column(name = "`group`")
    @Enumerated(EnumType.STRING)
    val group: TopicGroup,

    @Column(name = "name")
    val name: String,

    @Column(name = "created_date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
