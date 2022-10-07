package com.letters.to.personality.domain

import org.springframework.data.jpa.repository.JpaRepository

interface PersonalityRepository : JpaRepository<Personality, Long>
