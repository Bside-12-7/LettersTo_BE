package com.letters.to.letter.domain

import org.springframework.data.jpa.repository.JpaRepository

interface PublicLetterRepository : JpaRepository<PublicLetter, Long>
