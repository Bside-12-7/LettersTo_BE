package com.letters.to.auth.application

import com.fasterxml.jackson.annotation.JsonInclude
import com.letters.to.auth.domain.ProviderType

data class TokenProvideRequest(
    val idToken: String?,
    val code: String?,
    val providerType: ProviderType?
)

data class TokenRefreshRequest(
    val accessToken: String,
    val refreshToken: String
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TokenResponse(
    val registerToken: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val verified: Boolean
)
