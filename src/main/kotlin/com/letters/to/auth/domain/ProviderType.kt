package com.letters.to.auth.domain

enum class ProviderType(val issuer: String) {
    KAKAO("https://kauth.kakao.com"),
    APPLE("https://appleid.apple.com");

    companion object {
        fun findByIssuer(issuer: String): ProviderType {
            return values().first { it.issuer == issuer }
        }
    }
}
