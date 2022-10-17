package com.letters.to.member.application

import com.letters.to.auth.application.AccessTokenCreateService
import com.letters.to.auth.application.TokenResponse
import com.letters.to.auth.domain.Authentication
import com.letters.to.auth.domain.AuthenticationRepository
import com.letters.to.auth.domain.ProviderType
import com.letters.to.auth.domain.RegisterTokenRepository
import com.letters.to.auth.domain.findByRegisterToken
import com.letters.to.geolocation.domain.Geolocation
import com.letters.to.geolocation.domain.GeolocationRepository
import com.letters.to.member.domain.Member
import com.letters.to.member.domain.MemberRepository
import com.letters.to.member.domain.Nickname
import com.letters.to.personality.domain.Personality
import com.letters.to.personality.domain.PersonalityRepository
import com.letters.to.topic.domain.Topic
import com.letters.to.topic.domain.TopicRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberRegisterService(
    private val registerTokenRepository: RegisterTokenRepository,
    private val memberRepository: MemberRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val geolocationRepository: GeolocationRepository,
    private val topicRepository: TopicRepository,
    private val personalityRepository: PersonalityRepository,
    private val accessTokenCreateService: AccessTokenCreateService
) {
    @Transactional
    fun register(request: MemberRegisterRequest): TokenResponse {
        val registerToken = registerTokenRepository.findByRegisterToken(request.registerToken)
            ?: throw NoSuchElementException("유효한 회원가입 토큰이 아닙니다.")

        registerToken.verify()

        registerTokenRepository.delete(registerToken)

        val geolocation = geolocationRepository.findByIdOrNull(request.geolocationId)
            ?: throw NoSuchElementException("유효한 주소가 아닙니다.")
        val topics = topicRepository.findAllById(request.topicIds)
        val personalities = personalityRepository.findAllById(request.personalityIds)

        val member = createMember(
            nickname = Nickname(request.nickname),
            email = registerToken.body.email,
            geolocation = geolocation,
            topics = topics,
            personalities = personalities
        )

        val authentication = createAuthentication(
            providerType = registerToken.body.providerType,
            principal = registerToken.body.principal,
            member = member
        )

        val refreshToken = accessTokenCreateService.create(authentication)

        return TokenResponse(
            accessToken = refreshToken.accessToken,
            refreshToken = refreshToken.id,
            verified = true
        )
    }

    fun existsByNickname(nickname: Nickname): Boolean {
        return memberRepository.existsByNickname(nickname.value)
    }

    private fun createMember(
        nickname: Nickname,
        email: String,
        geolocation: Geolocation,
        topics: List<Topic>,
        personalities: List<Personality>
    ): Member {
        check(!memberRepository.existsByNickname(nickname.value)) { "이미 사용 중인 닉네임입니다." }

        val member = Member(
            nickname = nickname,
            email = email,
            geolocation = geolocation,
            topics = topics.toMutableList(),
            personalities = personalities.toMutableList()
        )

        return memberRepository.save(member)
    }

    private fun createAuthentication(providerType: ProviderType, principal: String, member: Member): Authentication {
        check(!authenticationRepository.existsByProviderTypeAndPrincipal(providerType, principal)) { "이미 연결된 계정입니다." }

        val authentication = Authentication(
            providerType = providerType,
            principal = principal,
            member = member
        )

        return authenticationRepository.save(authentication)
    }
}
