package com.letters.to.auth.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface AuthenticationRepository : JpaRepository<Authentication, Long> {
    fun findByProviderTypeAndPrincipal(providerType: ProviderType, principal: String): Authentication?
    fun existsByProviderTypeAndPrincipal(providerType: ProviderType, principal: String): Boolean

    @Query("delete from Authentication a where a.member.id = :memberId")
    @Modifying
    fun deleteByMemberId(memberId: Long)
}
