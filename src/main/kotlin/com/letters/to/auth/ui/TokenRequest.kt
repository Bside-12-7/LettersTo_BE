package com.letters.to.auth.ui

import com.fasterxml.jackson.annotation.JsonIgnore
import com.letters.to.auth.domain.ProviderType

data class TokenRequest(
    val idToken: String?,

    val providerType: ProviderType?,
    val code: String?,

    val accessToken: String?,
    val refreshToken: String?
) {
    @get:JsonIgnore
    val isOpenIdConnect: Boolean
        get() = idToken != null || code != null
}
