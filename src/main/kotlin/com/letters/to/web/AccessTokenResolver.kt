package com.letters.to.web

import com.letters.to.auth.application.AccessTokenVerifyService
import com.letters.to.auth.domain.AccessTokenPayload
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AccessTokenResolver(private val accessTokenVerifyService: AccessTokenVerifyService) :
    HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AccessToken::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): AccessTokenPayload {
        val authorization = webRequest.getHeader(AUTHORIZATION) ?: throw IllegalArgumentException("올바르지 않은 요청입니다.")
        val (tokenType, accessToken) = authorization.split(" ")

        if (tokenType != "Bearer") {
            throw AuthorizationException("올바르지 않은 요청입니다.")
        }

        try {
            return accessTokenVerifyService.verify(accessToken)
        } catch (e: Exception) {
            throw AuthorizationException("토큰이 유효하지 않습니다.")
        }
    }
}
