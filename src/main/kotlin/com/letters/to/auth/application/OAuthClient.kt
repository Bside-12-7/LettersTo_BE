package com.letters.to.auth.application

import com.letters.to.auth.domain.ProviderType

interface OAuthClient {
    val providerType: ProviderType
    fun idToken(code: String): String
}
