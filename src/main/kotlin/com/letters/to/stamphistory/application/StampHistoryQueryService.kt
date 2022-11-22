package com.letters.to.stamphistory.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StampHistoryQueryService(private val stampHistoryExtendsRepository: StampHistoryExtendsRepository) {
    fun findAllByLastMonth(memberId: Long): List<StampHistoryResponse> {
        return stampHistoryExtendsRepository.findAllByLastMonth(memberId)
    }
}
