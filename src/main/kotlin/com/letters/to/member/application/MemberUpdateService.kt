package com.letters.to.member.application

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.geolocation.domain.GeolocationRepository
import com.letters.to.member.domain.Member
import com.letters.to.member.domain.MemberRepository
import com.letters.to.member.domain.findByIdAndActive
import com.letters.to.personality.domain.PersonalityRepository
import com.letters.to.topic.domain.TopicRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberUpdateService(
    private val memberRepository: MemberRepository,
    private val topicRepository: TopicRepository,
    private val personalityRepository: PersonalityRepository,
    private val geolocationRepository: GeolocationRepository
) {

    fun update(accessTokenPayload: AccessTokenPayload, request: MemberUpdateRequest) {
        val member = getMember(accessTokenPayload.memberId)

        if (request.nickname != null) {
            updateNickname(member, request.nickname)
        }

        if (request.topicIds != null) {
            updateTopics(member, request.topicIds.toList())
        }

        if (request.personalityIds != null) {
            updatePersonalities(member, request.personalityIds.toList())
        }

        if (request.geolocationId != null) {
            updateGeolocation(member, request.geolocationId)
        }
    }

    private fun getMember(memberId: Long): Member {
        return memberRepository.findByIdAndActive(memberId) ?: throw NoSuchElementException("유효한 회원이 아닙니다.")
    }

    private fun updateNickname(member: Member, nickname: String) {
        check(!memberRepository.existsByNickname(nickname)) { "이미 사용 중인 닉네임입니다." }

        member.updateNickname(nickname)
    }

    private fun updateTopics(member: Member, topicIds: List<Long>) {
        val topics = topicRepository.findAllById(topicIds)

        member.updateTopics(topics)
    }

    private fun updatePersonalities(member: Member, personalityIds: List<Long>) {
        val personalities = personalityRepository.findAllById(personalityIds)

        member.updatePersonalities(personalities)
    }

    private fun updateGeolocation(member: Member, geolocationId: Long) {
        val geolocation = geolocationRepository.findByIdOrNull(geolocationId)
            ?: throw NoSuchElementException("유효한 주소가 아닙니다.")

        member.updateGeolocation(geolocation)
    }
}
