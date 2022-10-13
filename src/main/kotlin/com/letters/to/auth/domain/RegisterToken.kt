package com.letters.to.auth.domain

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "register_token")
@Entity
class RegisterToken(
    @Column(name = "body")
    @Convert(converter = RegisterInformationConverter::class)
    val body: RegisterInformation,

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

data class RegisterInformation(
    val providerType: ProviderType,
    val principal: String,
    val email: String,
)
