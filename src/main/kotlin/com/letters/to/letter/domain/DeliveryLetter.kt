package com.letters.to.letter.domain

import com.letters.to.geolocation.domain.Geolocation
import com.letters.to.member.domain.Member
import com.letters.to.stamp.domain.Stamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@DiscriminatorValue("DELIVERY")
class DeliveryLetter(
    title: Title = Title.UNTITLED,
    content: Content,
    pictures: MutableList<Picture> = mutableListOf(),
    paperType: PaperType,
    paperColor: PaperColor,
    stamp: Stamp,
    fromMember: Member,

    @Column(name = "delivery_type")
    val deliveryType: DeliveryType,
) : Letter(
    title = title,
    content = content,
    pictures = pictures,
    paperType = paperType,
    paperColor = paperColor,
    stamp = stamp,
    fromMember = fromMember
) {
    @ManyToOne
    @JoinColumn(name = "to_member_id")
    lateinit var toMember: Member
        private set

    @ManyToOne
    @JoinColumn(name = "from_geolocation_id")
    lateinit var fromGeolocation: Geolocation
        private set

    @ManyToOne
    @JoinColumn(name = "to_geolocation_id")
    lateinit var toGeolocation: Geolocation
        private set

    @Column(name = "replied")
    var replied: Boolean = false
        private set

    @Column(name = "`read`")
    var read: Boolean = false
        set(value) {
            if (field) {
                return
            }

            field = value
        }

    @Column(name = "delivery_date")
    lateinit var deliveryDate: LocalDateTime
        private set

    fun reply() {
        check(!replied) { "이미 답장한 편지입니다." }

        replied = true
    }

    fun sendTo(member: Member) {
        check(fromMember != member) { "자신에게 답장할 수 없습니다." }

        fromMember.useStamp(deliveryType.stampCount)

        toMember = member
        fromGeolocation = fromMember.geolocation
        toGeolocation = member.geolocation
        deliveryDate = deliveryType.deliveryDate(fromMember, toMember)
    }

    fun open() {
        check(deliveryDate <= LocalDateTime.now()) { "편지를 열어볼 수 없습니다." }

        read = true
    }
}
