package com.letters.to.auth.application

import com.auth0.jwk.JwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.letters.to.auth.config.OAuthProperties
import com.letters.to.auth.domain.AuthenticationRepository
import com.letters.to.auth.domain.ProviderType
import org.springframework.stereotype.Service
import java.security.interfaces.RSAPublicKey
import java.util.Date

@Service
class TokenProvideByOAuthService(
    private val authenticationRepository: AuthenticationRepository,
    private val accessTokenCreateService: AccessTokenCreateService,
    private val registerTokenCreateService: RegisterTokenCreateService,
    private val jwkProviders: List<Pair<ProviderType, JwkProvider>>,
    private val oauthPropertiesGroup: List<OAuthProperties>,
    private val oauthClients: List<OAuthClient>
) : TokenProvideService {
    override fun token(request: TokenProvideRequest): TokenResponse {
        val idToken = verify(request)
        val providerType = ProviderType.findByIssuer(idToken.issuer)
        val authentication = authenticationRepository.findByProviderTypeAndPrincipal(providerType, idToken.subject)

        if (authentication == null) {
            val registerToken =
                registerTokenCreateService.create(providerType, idToken.subject, idToken.getClaim("email").asString())

            return TokenResponse(registerToken = registerToken.id, verified = false)
        }

        val refreshToken = accessTokenCreateService.create(authentication)

        return TokenResponse(accessToken = refreshToken.accessToken, refreshToken = refreshToken.id, verified = true)
    }

    private fun verify(request: TokenProvideRequest): DecodedJWT {
        if (request.idToken != null) {
            val idToken = JWT.decode(request.idToken)
            val oauthProperties = oauthPropertiesGroup.first { it.providerType.issuer == idToken.issuer }

            verifyPayload(idToken.token, oauthProperties)
            verifySignature(idToken.keyId, idToken.token, oauthProperties)

            return idToken
        }

        val oauthClient = oauthClients.first { it.providerType == request.providerType }

        val idToken = oauthClient.idToken(code = request.code!!)

        return JWT.decode(idToken)
    }

    private fun verifyPayload(idToken: String, oauthProperties: OAuthProperties) {
        val jwt = JWT.decode(idToken)

        check(jwt.audience.any { oauthProperties.clientIds.contains(it) } && jwt.expiresAt.before(Date(System.currentTimeMillis()))) { "토큰이 유효하지 않습니다." }
    }

    private fun verifySignature(kid: String, idToken: String, oauthProperties: OAuthProperties) {
        val jwkProvider = jwkProviders.first { it.first == oauthProperties.providerType }.second
        val jwk = jwkProvider.get(kid)
        val algorithm = Algorithm.RSA256(jwk.publicKey as RSAPublicKey)
        val verifier = JWT.require(algorithm)
            .build()

        verifier.verify(idToken)
    }
}
