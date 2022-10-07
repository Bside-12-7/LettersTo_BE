package com.letters.to.auth.infra.kakao

import com.letters.to.auth.application.OAuthClient
import com.letters.to.auth.domain.ProviderType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

@Component
class KakaoAuthClient(
    private val kakaoAuthProperties: KakaoAuthProperties,
    private val restTemplate: RestTemplate
) : OAuthClient {
    override val providerType: ProviderType
        get() = ProviderType.KAKAO

    override fun idToken(code: String): String {
        val params = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "authorization_code")
            add("code", code)
            add("client_id", kakaoAuthProperties.clientId)
            add("client_secret", kakaoAuthProperties.clientSecret)
            add("redirect_uri", kakaoAuthProperties.redirectUri)
        }

        val (idToken) = restTemplate.postForObject(
            "https://kauth.kakao.com/oauth/token",
            params,
            KakaoTokenResponse::class.java
        )!!

        return idToken
    }
}
