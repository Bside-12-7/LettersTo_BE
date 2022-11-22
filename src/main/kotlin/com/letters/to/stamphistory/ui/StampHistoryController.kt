package com.letters.to.stamphistory.ui

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.stamphistory.application.StampHistoryQueryService
import com.letters.to.stamphistory.application.StampHistoryResponse
import com.letters.to.web.AccessToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/stamp-histories")
class StampHistoryController(private val stampHistoryQueryService: StampHistoryQueryService) {

    @GetMapping
    fun findAllByLastMonth(@AccessToken accessTokenPayload: AccessTokenPayload): List<StampHistoryResponse> {
        return stampHistoryQueryService.findAllByLastMonth(accessTokenPayload.memberId)
    }
}
