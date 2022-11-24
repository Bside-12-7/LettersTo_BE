package com.letters.to.stamphistory.domain

import org.springframework.data.jpa.repository.JpaRepository

interface StampHistoryRepository : JpaRepository<StampHistory, Long>
