package com.letters.to.letter.application

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.letter.domain.LetterBoxRepository
import com.letters.to.member.domain.MemberRepository
import com.letters.to.member.domain.findByIdAndActive
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LetterBoxQueryService(
    private val memberRepository: MemberRepository,
    private val letterBoxRepository: LetterBoxRepository,
    private val deliveryLetterExtendsRepository: DeliveryLetterExtendsRepository
) {
    fun find(accessTokenPayload: AccessTokenPayload): List<LetterBoxResponse> {
        val member = memberRepository.findByIdAndActive(accessTokenPayload.memberId)
            ?: throw NoSuchElementException("유효한 회원이 아닙니다.")

        val letterBoxes = letterBoxRepository.findByToMember(member)

        val newMap = deliveryLetterExtendsRepository.findFromMemberIdByNewDeliveredLetter(member.id)
            .toSet()

        return letterBoxes.map {
            LetterBoxResponse(
                fromMemberId = it.id,
                fromMemberNickname = it.fromMember.nickname.value,
                new = newMap.contains(it.id)
            )
        }
    }

    fun findDetail(accessTokenPayload: AccessTokenPayload, id: Long): LetterBoxDetailResponse {
        val letterBox = letterBoxRepository.findByIdOrNull(id) ?: throw NoSuchElementException("유효한 사서함이 아닙니다.")

        check(letterBox.toMember.id == accessTokenPayload.memberId) { "유효한 사서함이 아닙니다." }

        return LetterBoxDetailResponse(
            fromNickname = letterBox.fromMember.nickname.value,
            fromAddress = letterBox.fromMember.geolocation.fullname,
            topics = letterBox.fromMember.topics.map { it.name },
            personalities = letterBox.fromMember.personalities.map { it.name },
            startDate = letterBox.createdDate
        )
    }
}
