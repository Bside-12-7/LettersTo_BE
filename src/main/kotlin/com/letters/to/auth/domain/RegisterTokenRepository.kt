package com.letters.to.auth.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

interface RegisterTokenRepository : JpaRepository<RegisterToken, String>

fun RegisterTokenRepository.findByRegisterToken(registerToken: String): RegisterToken? = findByIdOrNull(registerToken)
