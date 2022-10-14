package com.letters.to.member.domain

import com.letters.to.geolocation.domain.Geolocation
import com.letters.to.personality.domain.Personality
import com.letters.to.topic.domain.Topic
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
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
    @Column(name = "nickname", unique = true)
    val nickname: Nickname,

    @Column(name = "email")
    val email: String,

    @ManyToOne
    @JoinColumn(name = "geolocation_id")
    val geolocation: Geolocation,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "member_topic",
        joinColumns = [JoinColumn(name = "member_id")],
        inverseJoinColumns = [JoinColumn(name = "topic_id")]
    )
    val topics: List<Topic> = mutableListOf(),

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "member_personality",
        joinColumns = [JoinColumn(name = "member_id")],
        inverseJoinColumns = [JoinColumn(name = "personality_id")]
    )
    val personalities: List<Personality> = mutableListOf(),

    @Column(name = "created_date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    init {
        require(topics.size in 1..10) { "관심사는 최소 1개이상 최대 10개이하 선택할 수 있습니다." }
        require(personalities.size in 1..12) { "성향은 최소 1개이상 최대 12개이하 선택할 수 있습니다." }
        require(geolocation.isCity) { "주소는 시군구까지 선택해야 합니다." }
    }
}
