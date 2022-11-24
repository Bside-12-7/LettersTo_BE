package com.letters.to.report.application

import com.letters.to.report.domain.Report
import com.letters.to.report.domain.ReportRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReportService(private val reportRepository: ReportRepository) {
    fun report(memberId: Long, request: ReportRequest) {
        check(!reportRepository.existsByMemberIdAndLetterId(memberId, request.letterId)) { "이미 신고 접수된 편지입니다." }

        val report = Report(
            memberId = memberId,
            letterId = request.letterId,
            description = request.description
        )

        reportRepository.save(report)
    }
}
