package com.letters.to.attendance.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface AttendanceRepository : JpaRepository<Attendance, Long> {
    fun existsByMemberIdAndDate(memberId: Long, date: LocalDate): Boolean
}
