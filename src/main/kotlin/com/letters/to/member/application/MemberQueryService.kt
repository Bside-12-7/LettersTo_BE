package com.letters.to.member.application

import com.letters.to.member.domain.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MemberQueryService(private val memberRepository: MemberRepository) {

    fun findById(id: Long): MemberResponse {
        val member = memberRepository.findByIdOrNull(id)
            ?: throw NoSuchElementException("유효한 회원이 아닙니다.")

        return MemberResponse(member = member)
    }
}
