package com.letters.to.member.application

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.member.domain.MemberRepository
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
        memberRepository.deleteById(accessTokenPayload.memberId)

        applicationEventPublisher.publishEvent(MemberWithdrawalEvent(accessTokenPayload.memberId))
    }
}
