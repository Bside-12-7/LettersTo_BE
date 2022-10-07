package com.letters.to.auth.infra.kakao

import com.letters.to.auth.config.OAuthProperties
import com.letters.to.auth.domain.ProviderType
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "kakao.auth")
data class KakaoAuthProperties(
    override val clientId: String,
    val clientSecret: String,
    val redirectUri: String
) : OAuthProperties {
    override val providerType: ProviderType
        get() = ProviderType.KAKAO
}
