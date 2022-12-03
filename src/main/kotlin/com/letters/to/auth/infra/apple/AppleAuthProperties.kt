package com.letters.to.auth.infra.apple

import com.letters.to.auth.config.OAuthProperties
import com.letters.to.auth.domain.ProviderType
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "apple.auth")
data class AppleAuthProperties(
    val restApiKey: String,
    val nativeAppKey: String,
    val clientSecret: String,
    val redirectUri: String
) : OAuthProperties {
    override val webClientId: String
        get() = restApiKey

    override val androidClientId: String
        get() = nativeAppKey

    override val iosClientId: String
        get() = nativeAppKey

    override val providerType: ProviderType
        get() = ProviderType.APPLE
}
