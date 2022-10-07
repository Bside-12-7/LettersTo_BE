package com.letters.to.auth.config

import com.letters.to.auth.domain.ProviderType

interface OAuthProperties {
    val clientId: String
    val providerType: ProviderType
}
