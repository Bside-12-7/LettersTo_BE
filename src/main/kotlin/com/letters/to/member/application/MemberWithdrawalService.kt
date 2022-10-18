package com.letters.to.member.application

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.member.domain.MemberRepository
import com.letters.to.member.domain.findByIdAndActive
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberWithdrawalService(
    private val memberRepository: MemberRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Transactional
    fun withdrawal(accessTokenPayload: AccessTokenPayload) {
        val member = memberRepository.findByIdAndActive(accessTokenPayload.memberId)
            ?: throw NoSuchElementException("유효한 회원이 아닙니다.")

        member.withdrawal()

        applicationEventPublisher.publishEvent(MemberWithdrawalEvent(accessTokenPayload.memberId))
    }
}
