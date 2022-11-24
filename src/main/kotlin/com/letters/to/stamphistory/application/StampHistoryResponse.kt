package com.letters.to.stamphistory.application

import com.letters.to.stamphistory.domain.StampHistoryType
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class StampHistoryResponse @QueryProjection constructor(
    val type: StampHistoryType,
    val quantity: Int,
    val createdDate: LocalDateTime,
    val id: Long
) {
    val description: String = type.description
}
