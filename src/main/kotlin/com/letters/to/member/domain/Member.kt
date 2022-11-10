package com.letters.to.member.domain

import com.letters.to.geolocation.domain.Geolocation
import com.letters.to.personality.domain.Personality
import com.letters.to.topic.domain.Topic
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table

@Table(name = "member")
@Entity
class Member(
    nickname: Nickname,
    email: String,
    geolocation: Geolocation,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "member_topic",
        joinColumns = [JoinColumn(name = "member_id")],
        inverseJoinColumns = [JoinColumn(name = "topic_id")]
    )
    val topics: MutableList<Topic> = mutableListOf(),

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "member_personality",
        joinColumns = [JoinColumn(name = "member_id")],
        inverseJoinColumns = [JoinColumn(name = "personality_id")]
    )
    val personalities: MutableList<Personality> = mutableListOf(),

    @Column(name = "created_date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    @Column(name = "nickname", unique = true)
    var nickname: Nickname = nickname
        private set

    @Column(name = "email")
    var email: String = email
        private set

    @ManyToOne
    @JoinColumn(name = "geolocation_id")
    var geolocation: Geolocation = geolocation
        private set

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: MemberStatus = MemberStatus.ACTIVE
        private set

    @Column(name = "stamp_quantity")
    var stampQuantity: Int = 0
        private set

    init {
        validateTopics(topics)
        validatePersonalities(personalities)
        validateGeolocation(geolocation)
    }

    fun updateNickname(nickname: String) {
        this.nickname = Nickname(nickname)
    }

    fun updateTopics(topics: List<Topic>) {
        validateTopics(topics)

        this.topics.clear()
        this.topics.addAll(topics)
    }

    private fun validateTopics(topics: List<Topic>) {
        require(topics.size in 1..10) { "관심사는 최소 1개이상 최대 10개이하 선택할 수 있습니다." }
    }

    fun updatePersonalities(personalities: List<Personality>) {
        validatePersonalities(personalities)

        this.personalities.clear()
        this.personalities.addAll(personalities)
    }

    private fun validatePersonalities(personalities: List<Personality>) {
        require(personalities.size in 1..12) { "성향은 최소 1개이상 최대 12개이하 선택할 수 있습니다." }
    }

    fun updateGeolocation(geolocation: Geolocation) {
        validateGeolocation(geolocation)

        this.geolocation = geolocation
    }

    private fun validateGeolocation(geolocation: Geolocation) {
        require(geolocation.isCity) { "주소는 시군구까지 선택해야 합니다." }
    }

    fun withdrawal() {
        this.status = MemberStatus.WITHDRAWAL
        this.email = ""
        this.topics.clear()
        this.personalities.clear()
    }

    fun useStamp(count: Int = 1) {
        require(count > 0) { "우표는 1개 이상 사용해야 합니다." }
        require(stampQuantity - count >= 0) { "우표가 부족합니다." }

        stampQuantity -= count
    }

    fun giveStamp(count: Int = 1) {
        require(count > 0) { "우표는 1개 이상 지급해야 합니다." }

        stampQuantity += count
    }
}
