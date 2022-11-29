package com.letters.to.letter.ui

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.letter.application.DeliveryLetterMoreResponse
import com.letters.to.letter.application.DeliveryLetterOpenService
import com.letters.to.letter.application.DeliveryLetterQueryService
import com.letters.to.letter.application.DeliveryLetterWriteRequest
import com.letters.to.letter.application.DeliveryLetterWriteService
import com.letters.to.letter.application.LetterDetailResponse
import com.letters.to.web.AccessToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/delivery-letters")
class DeliveryLetterController(
    private val deliveryLetterWriteService: DeliveryLetterWriteService,
    private val deliveryLetterQueryService: DeliveryLetterQueryService,
    private val deliveryLetterOpenService: DeliveryLetterOpenService
) {
    @PostMapping
    fun write(
        @AccessToken accessTokenPayload: AccessTokenPayload,
        @Valid @RequestBody request: DeliveryLetterWriteRequest
    ) {
        deliveryLetterWriteService.write(accessTokenPayload, request)
    }

    @GetMapping
    fun findMoreBy(
        @AccessToken accessTokenPayload: AccessTokenPayload,
        @RequestParam cursor: Long?,
        @RequestParam fromMemberId: Long
    ): DeliveryLetterMoreResponse {
        return deliveryLetterQueryService.findMoreBy(accessTokenPayload, fromMemberId, cursor)
    }

    @PostMapping("/{id}/open")
    fun open(
        @AccessToken accessTokenPayload: AccessTokenPayload,
        @PathVariable id: Long
    ): LetterDetailResponse {
        return deliveryLetterOpenService.open(accessTokenPayload.memberId, id)
    }
}
