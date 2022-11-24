package com.letters.to.report.domain

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "report")
class Report(
    @Column(name = "letter_id")
    val letterId: Long,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "description")
    val description: String,

    @Column(name = "completed")
    val completed: Boolean = false,

    @Column(name = "created_date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
