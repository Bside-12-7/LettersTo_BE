package com.letters.to.auth.domain

import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {
    fun deleteByAccessToken(accessToken: String)
}

fun RefreshTokenRepository.findByRefreshToken(refreshToken: String): RefreshToken? = findById(refreshToken).orElse(null)
