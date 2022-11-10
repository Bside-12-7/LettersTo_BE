package com.letters.to.letter.application

import com.letters.to.letter.domain.DeliveryLetter
import com.letters.to.letter.domain.DeliveryType
import com.letters.to.letter.domain.PaperColor
import com.letters.to.letter.domain.QDeliveryLetter.deliveryLetter
import com.querydsl.core.annotations.QueryProjection
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DeliveryLetterQueryRepository(private val jpaQueryFactory: JPAQueryFactory) :
    DeliveryLetterExtendsRepository {
    override fun findMoreBy(cursor: Long?, fromMemberId: Long, toMemberId: Long): List<DeliveryLetterResponse> {
        val deliveryLetters = jpaQueryFactory.from(deliveryLetter)
            .where(
                cursor?.let { deliveryLetter.id.lt(it) },
                deliveryLetter.fromMember.id.eq(fromMemberId).and(deliveryLetter.toMember.id.eq(toMemberId)).or(
                    deliveryLetter.toMember.id.eq(fromMemberId).and(deliveryLetter.fromMember.id.eq(toMemberId))
                ),
                deliveryLetter.deliveryDate.loe(LocalDateTime.now())
            )
            .orderBy(deliveryLetter.id.desc())
            .select(
                QDeliveryLetterProjection(
                    deliveryLetter.id,
                    deliveryLetter.title,
                    deliveryLetter.fromMember.id,
                    deliveryLetter.fromGeolocation.fullname,
                    deliveryLetter.fromMember.nickname,
                    deliveryLetter.toGeolocation.fullname,
                    deliveryLetter.toMember.nickname,
                    deliveryLetter.paperColor,
                    deliveryLetter.stamp.id,
                    deliveryLetter.read,
                    deliveryLetter.deliveryType,
                    deliveryLetter.deliveryDate,
                )
            )
            .limit(10)
            .fetch()

        return deliveryLetters.map {
            DeliveryLetterResponse(
                id = it.id,
                title = it.title,
                fromAddress = it.fromAddress,
                fromNickname = it.fromNickname,
                toAddress = it.toAddress,
                toNickname = it.toNickname,
                paperColor = it.paperColor,
                stampId = it.stampId,
                read = it.read,
                me = it.fromMemberId == toMemberId,
                deliveryType = it.deliveryType,
                deliveryDate = it.deliveryDate
            )
        }
    }

    override fun findOneBy(memberId: Long, id: Long): DeliveryLetter? {
        return jpaQueryFactory.selectFrom(deliveryLetter)
            .innerJoin(deliveryLetter.toMember)
            .fetchJoin()
            .innerJoin(deliveryLetter.fromMember)
            .fetchJoin()
            .innerJoin(deliveryLetter.fromGeolocation)
            .fetchJoin()
            .innerJoin(deliveryLetter.toGeolocation)
            .fetchJoin()
            .where(
                deliveryLetter.id.eq(id),
                deliveryLetter.toMember.id.eq(memberId).or(deliveryLetter.fromMember.id.eq(memberId)),
                deliveryLetter.deliveryDate.loe(LocalDateTime.now())
            )
            .fetchOne()
    }

    override fun findFromMemberIdByNewDeliveredLetter(toMemberId: Long): List<Long> {
        return jpaQueryFactory.from(deliveryLetter)
            .where(
                deliveryLetter.toMember.id.eq(toMemberId),
                deliveryLetter.read.isFalse,
                deliveryLetter.deliveryDate.loe(LocalDateTime.now())
            )
            .select(deliveryLetter.fromMember.id)
            .fetch()
    }
}

data class DeliveryLetterProjection @QueryProjection constructor(
    val id: Long,
    val title: String,
    val fromMemberId: Long,
    val fromAddress: String,
    val fromNickname: String,
    val toAddress: String,
    val toNickname: String,
    val paperColor: PaperColor,
    val stampId: Long,
    val read: Boolean,
    val deliveryType: DeliveryType,
    val deliveryDate: LocalDateTime,
)
