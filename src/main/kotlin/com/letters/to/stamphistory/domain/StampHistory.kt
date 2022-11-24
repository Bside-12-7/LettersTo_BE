package com.letters.to.stamphistory.domain

import com.letters.to.member.domain.Member
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "stamp_history")
class StampHistory(
    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    val type: StampHistoryType,

    @Column(name = "quantity")
    val quantity: Int,

    @Column(name = "created_date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
