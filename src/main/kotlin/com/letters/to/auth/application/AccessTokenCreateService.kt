package com.letters.to.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.letters.to.auth.config.TokenProperties
import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.auth.domain.Authentication
import com.letters.to.auth.domain.RefreshToken
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AccessTokenCreateService(
    private val refreshTokenCreateService: RefreshTokenCreateService,
    private val accessTokenVerifyService: AccessTokenVerifyService,
    private val tokenProperties: TokenProperties
) {
    fun create(authentication: Authentication): RefreshToken {
        return create(authentication.accessTokenPayload)
    }

    fun create(accessToken: String): RefreshToken {
        return create(accessTokenVerifyService.verify(accessToken, true))
    }

    private fun create(accessTokenPayload: AccessTokenPayload): RefreshToken {
        val accessToken = JWT.create()
            .withClaim("providerType", accessTokenPayload.providerType.name)
            .withClaim("principal", accessTokenPayload.principal)
            .withClaim("memberId", accessTokenPayload.memberId)
            .withExpiresAt(Date(System.currentTimeMillis() + tokenProperties.accessTokenExpiresIn * 1000))
            .sign(Algorithm.HMAC256(tokenProperties.secret))

        return refreshTokenCreateService.create(accessToken)
    }
}
