package com.letters.to.attendance.domain

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "attendance")
class Attendance(
    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "`date`")
    val date: LocalDate = LocalDate.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
