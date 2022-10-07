package com.letters.to.auth.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class AccessTokenPayload(
    val providerType: ProviderType,
    val principal: String,
    val memberId: Long
)
