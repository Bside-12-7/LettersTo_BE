package com.letters.to.auth.ui

import com.letters.to.auth.application.TokenProvideByOAuthService
import com.letters.to.auth.application.TokenProvideRequest
import com.letters.to.auth.application.TokenRefreshRequest
import com.letters.to.auth.application.TokenRefreshService
import com.letters.to.auth.application.TokenResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/token")
class TokenController(
    private val tokenProvideService: TokenProvideByOAuthService,
    private val tokenRefreshService: TokenRefreshService
) {

    @PostMapping
    fun token(@RequestBody request: TokenRequest): TokenResponse {
        if (request.isOpenIdConnect) {
            return tokenProvideService.token(
                TokenProvideRequest(
                    idToken = request.idToken,
                    code = request.code,
                    providerType = request.providerType
                )
            )
        }

        return tokenRefreshService.refresh(
            TokenRefreshRequest(
                accessToken = request.accessToken!!,
                refreshToken = request.refreshToken!!
            )
        )
    }
}
