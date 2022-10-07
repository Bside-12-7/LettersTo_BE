package com.letters.to.personality.application

import com.letters.to.personality.domain.Personality

data class PersonalityResponse(
    val id: Long,
    val name: String
) {
    constructor(personality: Personality) : this(id = personality.id, name = personality.name)
}
