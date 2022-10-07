package com.letters.to.auth.domain

import org.springframework.data.jpa.repository.JpaRepository

interface AuthenticationRepository : JpaRepository<Authentication, Long> {
    fun findByProviderTypeAndPrincipal(providerType: ProviderType, principal: String): Authentication?
    fun existsByProviderTypeAndPrincipal(providerType: ProviderType, principal: String): Boolean
}
