package com.letters.to.personality.application

import com.letters.to.personality.domain.PersonalityRepository
import org.springframework.stereotype.Service

@Service
class PersonalityQueryService(
    private val personalityRepository: PersonalityRepository
) {
    fun findAll(): List<PersonalityResponse> {
        return personalityRepository.findAll()
            .map { PersonalityResponse(it) }
    }
}
