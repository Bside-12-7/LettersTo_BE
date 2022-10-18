package com.letters.to.member.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByNickname(nickname: String): Boolean
    fun findByIdAndStatus(id: Long, status: MemberStatus): Member?
}

fun MemberRepository.findByIdAndActive(id: Long): Member? = findByIdAndStatus(id, MemberStatus.ACTIVE)
