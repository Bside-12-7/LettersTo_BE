package com.letters.to.auth.ui

import com.letters.to.auth.domain.ProviderType

data class TokenRequest(
    val idToken: String?,
    val code: String?,
    val providerType: ProviderType?,
    val accessToken: String?,
    val refreshToken: String?
) {
    val isOpenIdConnect: Boolean
        get() = idToken != null || code != null
}
