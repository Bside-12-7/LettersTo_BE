package com.letters.to.letter.ui

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.letter.application.DeliveryLetterWriteRequest
import com.letters.to.letter.application.LetterDetailResponse
import com.letters.to.letter.application.PublicLetterMoreResponse
import com.letters.to.letter.application.PublicLetterQueryService
import com.letters.to.letter.application.PublicLetterWriteRequest
import com.letters.to.letter.application.PublicLetterWriteService
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
@RequestMapping("/api/public-letters")
class PublicLetterController(
    private val publicLetterWriteService: PublicLetterWriteService,
    private val publicLetterQueryService: PublicLetterQueryService
) {
    @PostMapping
    fun write(
        @AccessToken accessTokenPayload: AccessTokenPayload,
        @Valid @RequestBody request: PublicLetterWriteRequest
    ) {
        publicLetterWriteService.write(accessTokenPayload, request)
    }

    @PostMapping("/reply")
    fun reply(
        @AccessToken accessTokenPayload: AccessTokenPayload,
        @Valid @RequestBody request: DeliveryLetterWriteRequest
    ) {
        publicLetterWriteService.reply(accessTokenPayload, request)
    }

    @GetMapping
    fun findMoreBy(@RequestParam cursor: Long?): PublicLetterMoreResponse {
        return publicLetterQueryService.findMoreBy(cursor)
    }

    @GetMapping("/{id}")
    fun findOneBy(@AccessToken accessTokenPayload: AccessTokenPayload, @PathVariable id: Long): LetterDetailResponse {
        return publicLetterQueryService.findOneBy(accessTokenPayload.memberId, id)
    }
}
