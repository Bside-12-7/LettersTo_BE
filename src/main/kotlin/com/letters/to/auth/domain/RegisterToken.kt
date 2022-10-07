package com.letters.to.auth.domain

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "register_token")
@Entity
class RegisterToken(
    @Column(name = "provider_type")
    @Enumerated(EnumType.STRING)
    val providerType: ProviderType,

    @Column(name = "principal")
    val principal: String,

    @Column(name = "email")
    val email: String,

    @Column(name = "expires_in")
    val expiresIn: Int,

    @Column(name = "created_date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    val id: String = UUID.randomUUID().toString()
) {
    val verified: Boolean
        get() = createdDate.plusSeconds(expiresIn.toLong()) > LocalDateTime.now()

    fun verify() {
        check(verified) { "회원가입 토큰이 유효하지 않습니다." }
    }
}
