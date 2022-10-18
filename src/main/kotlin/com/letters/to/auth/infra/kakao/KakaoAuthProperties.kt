package com.letters.to.auth.infra.kakao

import com.letters.to.auth.config.OAuthProperties
import com.letters.to.auth.domain.ProviderType
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "kakao.auth")
data class KakaoAuthProperties(
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
        get() = ProviderType.KAKAO
}
