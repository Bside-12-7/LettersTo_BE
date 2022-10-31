package com.letters.to.file.domain

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Table(
    name = "file",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["bucket", "`key`"])
    ]
)
@Entity
class File(
    @Column(name = "bucket")
    val bucket: String,

    @Column(name = "`key`")
    val key: String,

    @Column(name = "expired_date")
    val expiredDate: LocalDateTime,

    @Column(name = "created_date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    val id: String = UUID.randomUUID().toString()
) {
    @Column(name = "verified")
    var verified: Boolean = false
        private set

    fun confirm() {
        this.verified = true
    }
}
