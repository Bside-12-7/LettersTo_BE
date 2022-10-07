package com.letters.to.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("token")
data class TokenProperties(
    val registerTokenExpiresIn: Int,
    val accessTokenExpiresIn: Int,
    val refreshTokenExpiresIn: Int,
    val secret: String
)
