package com.letters.to.auth.domain

import com.letters.to.member.domain.Member
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Table(
    name = "authentication",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["provider_type", "principal"])
    ]
)
@Entity
class Authentication(
    @Column(name = "provider_type")
    @Enumerated(EnumType.STRING)
    val providerType: ProviderType,

    @Column(name = "principal")
    val principal: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @Column(name = "created_date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    val accessTokenPayload: AccessTokenPayload
        get() = AccessTokenPayload(providerType = providerType, principal = principal, memberId = member.id)
}
