package com.letters.to.auth.config

import com.letters.to.auth.domain.ProviderType

interface OAuthProperties {
    val webClientId: String
    val androidClientId: String
    val iosClientId: String
    val clientIds: List<String>
        get() = listOf(webClientId, androidClientId, iosClientId)
    val providerType: ProviderType
}
