package com.letters.to.attendance.ui

import com.letters.to.attendance.application.AttendanceService
import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.web.AccessToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/attendances")
class AttendanceController(private val attendanceService: AttendanceService) {

    @PostMapping
    fun attendance(@AccessToken accessTokenPayload: AccessTokenPayload) {
        attendanceService.attendance(accessTokenPayload.memberId)
    }
}
