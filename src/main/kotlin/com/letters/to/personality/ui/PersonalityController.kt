package com.letters.to.personality.ui

import com.letters.to.personality.application.PersonalityQueryService
import com.letters.to.personality.application.PersonalityResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/personalities")
class PersonalityController(private val personalityQueryService: PersonalityQueryService) {

    @GetMapping
    fun findAll(): List<PersonalityResponse> {
        return personalityQueryService.findAll()
    }
}
