package com.letters.to.auth.application

import com.letters.to.auth.config.TokenProperties
import com.letters.to.auth.domain.RefreshToken
import com.letters.to.auth.domain.RefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class RefreshTokenCreateService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenProperties: TokenProperties
) {
    fun create(accessToken: String): RefreshToken {
        val refreshToken = RefreshToken(accessToken = accessToken, expiresIn = tokenProperties.refreshTokenExpiresIn)

        return refreshTokenRepository.save(refreshToken)
    }
}
