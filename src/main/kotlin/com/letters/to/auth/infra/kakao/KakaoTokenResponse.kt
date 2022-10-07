package com.letters.to.auth.infra.kakao

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoTokenResponse(
    @JsonAlias("id_token")
    val idToken: String
)
