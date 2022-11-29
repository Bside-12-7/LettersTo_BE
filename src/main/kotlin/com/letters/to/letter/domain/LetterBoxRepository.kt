package com.letters.to.letter.domain

import com.letters.to.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface LetterBoxRepository : JpaRepository<LetterBox, Long> {
    fun existsByToMemberAndFromMember(member: Member, fromMember: Member): Boolean
    fun findByToMember(toMember: Member): List<LetterBox>
}
