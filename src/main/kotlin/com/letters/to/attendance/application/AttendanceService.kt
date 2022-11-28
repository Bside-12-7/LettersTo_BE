package com.letters.to.attendance.application

import com.letters.to.attendance.domain.Attendance
import com.letters.to.attendance.domain.AttendanceRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class AttendanceService(
    private val attendanceRepository: AttendanceRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun attendance(memberId: Long) {
        if (attendanceRepository.existsByMemberIdAndDate(memberId, LocalDate.now())) {
            return
        }

        val attendance = Attendance(memberId)

        attendanceRepository.save(attendance)

        applicationEventPublisher.publishEvent(AttendanceEvent(memberId = memberId))
    }
}
