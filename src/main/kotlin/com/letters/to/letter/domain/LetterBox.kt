package com.letters.to.letter.domain

import com.letters.to.member.domain.Member
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Table(
    name = "letter_box",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["from_member_id", "to_member_id"])
    ]
)
@Entity
class LetterBox(
    @ManyToOne
    @JoinColumn(name = "from_member_id")
    val fromMember: Member,

    @ManyToOne
    @JoinColumn(name = "to_member_id")
    val toMember: Member,

    @Column(name = "created_date")
    val createdDate: LocalDateTime,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
