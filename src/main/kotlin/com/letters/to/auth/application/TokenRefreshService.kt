package com.letters.to.auth.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.auth.domain.RefreshTokenRepository
import com.letters.to.auth.domain.findByRefreshToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Base64Utils

@Service
class TokenRefreshService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val accessTokenCreateService: AccessTokenCreateService,
    private val objectMapper: ObjectMapper
) {
    @Transactional
    fun refresh(request: TokenRefreshRequest): TokenResponse {
        val accessTokenPayload = verify(request)

        refreshTokenRepository.deleteByAccessToken(request.accessToken)

        val refreshToken = accessTokenCreateService.create(accessTokenPayload)

        return TokenResponse(accessToken = refreshToken.accessToken, refreshToken = refreshToken.id, verified = true)
    }

    private fun verify(request: TokenRefreshRequest): AccessTokenPayload {
        val refreshToken = refreshTokenRepository.findByRefreshToken(request.refreshToken)
            ?: throw NoSuchElementException("리프레시 토큰이 유효하지 않습니다.")

        refreshToken.verify(request.accessToken)

        val payload = request.accessToken.split(".")[1]

        return objectMapper.readValue(Base64Utils.decodeFromString(payload), AccessTokenPayload::class.java)
    }
}
