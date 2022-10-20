package com.letters.to.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.letters.to.auth.config.TokenProperties
import com.letters.to.auth.domain.AccessTokenPayload
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils

@Service
class AccessTokenVerifyService(
    private val objectMapper: ObjectMapper,
    tokenProperties: TokenProperties
) {
    private val verifierBuilder = JWT.require(Algorithm.HMAC256(tokenProperties.secret))

    private val verifier: JWTVerifier = verifierBuilder.build()

    private val refreshVerifier: JWTVerifier =
        verifierBuilder.acceptExpiresAt(tokenProperties.refreshTokenExpiresIn.toLong())
            .build()

    fun verify(accessToken: String, refresh: Boolean = false): AccessTokenPayload {
        val jwt = if (refresh) refreshVerifier.verify(accessToken) else verifier.verify(accessToken)

        return objectMapper.readValue(Base64Utils.decodeFromString(jwt.payload), AccessTokenPayload::class.java)
    }
}
