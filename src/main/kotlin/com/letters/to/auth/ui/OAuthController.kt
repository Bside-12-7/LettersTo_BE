package com.letters.to.auth.ui

import com.letters.to.auth.infra.kakao.KakaoAuthProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/oauth")
class OAuthController(private val kakaoAuthProperties: KakaoAuthProperties) {

    @GetMapping("/kakao")
    fun kakao() =
        RedirectView("https://kauth.kakao.com/oauth/authorize?client_id=${kakaoAuthProperties.webClientId}&redirect_uri=${kakaoAuthProperties.redirectUri}&response_type=code")
}
