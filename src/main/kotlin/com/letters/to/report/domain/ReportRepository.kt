package com.letters.to.report.domain

import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository : JpaRepository<Report, Long> {
    fun existsByMemberIdAndLetterId(memberId: Long, letterId: Long): Boolean
}
