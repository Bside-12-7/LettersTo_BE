package com.letters.to.letter.ui

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.letter.application.LetterBoxDetailResponse
import com.letters.to.letter.application.LetterBoxQueryService
import com.letters.to.letter.application.LetterBoxResponse
import com.letters.to.web.AccessToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/letter-boxes")
class LetterBoxController(private val letterBoxQueryService: LetterBoxQueryService) {

    @GetMapping
    fun find(@AccessToken accessTokenPayload: AccessTokenPayload): List<LetterBoxResponse> {
        return letterBoxQueryService.find(accessTokenPayload)
    }

    @GetMapping("/{id}")
    fun findDetail(
        @AccessToken accessTokenPayload: AccessTokenPayload,
        @PathVariable id: Long
    ): LetterBoxDetailResponse {
        return letterBoxQueryService.findDetail(accessTokenPayload, id)
    }
}
