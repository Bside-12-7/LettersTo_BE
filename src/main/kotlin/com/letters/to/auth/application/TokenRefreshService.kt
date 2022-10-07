package com.letters.to.auth.application

import com.letters.to.auth.domain.RefreshTokenRepository
import com.letters.to.auth.domain.findByRefreshToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenRefreshService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val accessTokenCreateService: AccessTokenCreateService
) {
    @Transactional
    fun refresh(request: TokenRefreshRequest): TokenResponse {
        verify(request)

        refreshTokenRepository.deleteByAccessToken(request.accessToken)

        val refreshToken = accessTokenCreateService.create(request.accessToken)

        return TokenResponse(accessToken = refreshToken.accessToken, refreshToken = refreshToken.id, verified = true)
    }

    private fun verify(request: TokenRefreshRequest) {
        val refreshToken = refreshTokenRepository.findByRefreshToken(request.refreshToken)
            ?: throw IllegalStateException("리프레시 토큰이 존재하지 않습니다.")

        refreshToken.verify(request.accessToken)
    }
}
