package com.letters.to.auth.domain

import org.springframework.data.jpa.repository.JpaRepository

interface RegisterTokenRepository : JpaRepository<RegisterToken, String>

fun RegisterTokenRepository.findByRegisterToken(registerToken: String): RegisterToken? =
    findById(registerToken).orElse(null)
