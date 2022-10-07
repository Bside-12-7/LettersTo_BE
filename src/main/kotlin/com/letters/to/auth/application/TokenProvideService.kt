package com.letters.to.auth.application

interface TokenProvideService {
    fun token(request: TokenProvideRequest): TokenResponse
}
