package com.letters.to.stamphistory.application

interface StampHistoryExtendsRepository {
    fun findAllByLastMonth(memberId: Long): List<StampHistoryResponse>
}
