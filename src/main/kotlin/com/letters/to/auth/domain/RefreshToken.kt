package com.letters.to.auth.domain

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "refresh_token")
@Entity
class RefreshToken(
    @Column(name = "access_token")
    val accessToken: String,

    @Column(name = "expires_in")
    val expiresIn: Int,

    @Column(name = "created_date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    val id: String = UUID.randomUUID().toString()
) {
    val verified: Boolean
        get() = createdDate.plusSeconds(expiresIn.toLong()) > LocalDateTime.now()

    fun verify(accessToken: String) {
        check(verified && this.accessToken == accessToken) { "리프레시 토큰이 유효하지 않습니다." }
    }
}
