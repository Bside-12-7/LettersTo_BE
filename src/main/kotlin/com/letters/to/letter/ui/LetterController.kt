package com.letters.to.letter.ui

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.letter.application.DeliveryDateResponse
import com.letters.to.letter.application.DeliveryDateService
import com.letters.to.letter.domain.DeliveryType
import com.letters.to.web.AccessToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/letters")
class LetterController(private val deliveryDateService: DeliveryDateService) {
    @GetMapping("/{id}/delivery-date")
    fun deliveryDate(
        @AccessToken accessTokenPayload: AccessTokenPayload,
        @PathVariable id: Long,
        @RequestParam deliveryType: DeliveryType
    ): DeliveryDateResponse {
        return deliveryDateService.deliveryDate(accessTokenPayload.memberId, id, deliveryType)
    }
}
