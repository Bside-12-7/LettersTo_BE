package com.letters.to.report.ui

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.report.application.ReportRequest
import com.letters.to.report.application.ReportService
import com.letters.to.web.AccessToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/reports")
class ReportController(private val reportService: ReportService) {

    @PostMapping
    fun report(@AccessToken accessTokenPayload: AccessTokenPayload, @RequestBody @Valid request: ReportRequest) {
        reportService.report(accessTokenPayload.memberId, request)
    }
}
