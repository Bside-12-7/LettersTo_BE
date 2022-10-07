package com.letters.to.auth.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {
    fun deleteByAccessToken(accessToken: String)
}

fun RefreshTokenRepository.findByRefreshToken(refreshToken: String): RefreshToken? = findByIdOrNull(refreshToken)
