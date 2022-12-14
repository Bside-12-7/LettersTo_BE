package com.letters.to.auth.config

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import com.letters.to.auth.domain.ProviderType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URL
import java.util.concurrent.TimeUnit

@Configuration
class JwkConfig {

    @Bean
    fun kakaoJwkProvider(): Pair<ProviderType, JwkProvider> {
        return ProviderType.KAKAO to JwkProviderBuilder("https://kauth.kakao.com")
            .cached(10, 5, TimeUnit.HOURS)
            .build()
    }

    @Bean
    fun appleJwkProvider(): Pair<ProviderType, JwkProvider> {
        return ProviderType.APPLE to JwkProviderBuilder(URL("https://appleid.apple.com/auth/keys"))
            .cached(10, 5, TimeUnit.HOURS)
            .build()
    }
}
