package com.letters.to.report.application

import org.hibernate.validator.constraints.Length

data class ReportRequest(
    val letterId: Long,

    @field:Length(max = 100)
    val description: String
)
