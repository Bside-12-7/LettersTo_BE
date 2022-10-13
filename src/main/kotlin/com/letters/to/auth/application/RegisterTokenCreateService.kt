package com.letters.to.auth.application

import com.letters.to.auth.config.TokenProperties
import com.letters.to.auth.domain.ProviderType
import com.letters.to.auth.domain.RegisterInformation
import com.letters.to.auth.domain.RegisterToken
import com.letters.to.auth.domain.RegisterTokenRepository
import org.springframework.stereotype.Service

@Service
class RegisterTokenCreateService(
    private val registerTokenRepository: RegisterTokenRepository,
    private val tokenProperties: TokenProperties
) {
    fun create(providerType: ProviderType, principal: String, email: String): RegisterToken {
        val registerToken = RegisterToken(
            body = RegisterInformation(
                providerType = providerType,
                principal = principal,
                email = email
            ),
            expiresIn = tokenProperties.registerTokenExpiresIn
        )

        return registerTokenRepository.save(registerToken)
    }
}
