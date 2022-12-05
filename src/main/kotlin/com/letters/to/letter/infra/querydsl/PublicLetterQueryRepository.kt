package com.letters.to.letter.infra.querydsl

import com.letters.to.geolocation.domain.QGeolocation.geolocation
import com.letters.to.letter.application.LetterDetailResponse
import com.letters.to.letter.application.PublicLetterExtendsRepository
import com.letters.to.letter.application.PublicLetterResponse
import com.letters.to.letter.domain.AlignType
import com.letters.to.letter.domain.PaperColor
import com.letters.to.letter.domain.PaperType
import com.letters.to.letter.domain.QLetterBox.letterBox
import com.letters.to.letter.domain.QPicture.picture
import com.letters.to.letter.domain.QPublicLetter.publicLetter
import com.letters.to.member.domain.MemberStatus
import com.letters.to.member.domain.QMember.member
import com.letters.to.personality.domain.QPersonality.personality
import com.letters.to.stamp.domain.QStamp.stamp
import com.letters.to.topic.domain.QTopic.topic
import com.querydsl.core.annotations.QueryProjection
import com.querydsl.core.group.GroupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PublicLetterQueryRepository(private val jpaQueryFactory: JPAQueryFactory) : PublicLetterExtendsRepository {
    override fun findMoreBy(cursor: Long?): List<PublicLetterResponse> {
        val publicLetters = jpaQueryFactory.from(publicLetter)
            .innerJoin(publicLetter.fromMember, member)
            .innerJoin(member.geolocation, geolocation)
            .innerJoin(publicLetter.stamp, stamp)
            .where(
                cursor?.let { publicLetter.id.lt(it) },
                member.status.eq(MemberStatus.ACTIVE)
            )
            .orderBy(publicLetter.id.desc())
            .limit(10)
            .select(
                QPublicLetterProjection(
                    publicLetter.id,
                    publicLetter.title,
                    geolocation.fullname,
                    publicLetter.fromMember.nickname,
                    publicLetter.paperColor,
                    publicLetter.stamp.id,
                    publicLetter.createdDate
                )
            )
            .fetch()

        val topics = jpaQueryFactory.from(publicLetter)
            .innerJoin(publicLetter.topics, topic)
            .where(publicLetter.id.`in`(publicLetters.map { it.id }))
            .transform(GroupBy.groupBy(publicLetter.id).`as`(list(topic.name)))

        val personalities = jpaQueryFactory.from(publicLetter)
            .innerJoin(publicLetter.personalities, personality)
            .where(publicLetter.id.`in`(publicLetters.map { it.id }))
            .transform(GroupBy.groupBy(publicLetter.id).`as`(list(personality.name)))

        return publicLetters.map {
            PublicLetterResponse(
                id = it.id,
                title = it.title,
                fromAddress = it.fromAddress,
                fromNickname = it.fromNickname,
                paperColor = it.paperColor,
                stampId = it.stampId,
                topics = topics[it.id].orEmpty(),
                personalities = personalities[it.id].orEmpty(),
                createdDate = it.createdDate
            )
        }
    }

    override fun findOneBy(memberId: Long, id: Long): LetterDetailResponse? {
        val publicLetter = jpaQueryFactory.from(publicLetter)
            .innerJoin(publicLetter.fromMember, member)
            .innerJoin(member.geolocation, geolocation)
            .where(publicLetter.id.eq(id))
            .select(
                QPublicLetterDetailProjection(
                    publicLetter.id,
                    publicLetter.title,
                    publicLetter.content,
                    publicLetter.fromMember.id,
                    geolocation.fullname,
                    publicLetter.fromMember.nickname,
                    publicLetter.paperColor,
                    publicLetter.paperType,
                    publicLetter.alignType,
                    publicLetter.stamp.id,
                    publicLetter.createdDate
                )
            )
            .fetchOne()

        val files = jpaQueryFactory.from(picture)
            .where(picture.letter.id.eq(id))
            .select(picture.fileId)
            .fetch()

        return publicLetter?.let {
            val exists = jpaQueryFactory.from(letterBox)
                .where(letterBox.fromMember.id.eq(memberId), letterBox.toMember.id.eq(it.fromId))
                .select(Expressions.ONE)
                .limit(1)
                .fetchOne() != null

            LetterDetailResponse(
                id = it.id,
                title = it.title,
                content = it.content,
                fromAddress = it.fromAddress,
                fromNickname = it.fromNickname,
                paperColor = it.paperColor,
                paperType = it.paperType,
                alignType = it.alignType,
                stampId = it.stampId,
                replied = false,
                canReply = it.fromId != memberId && !exists,
                files = files,
                createdDate = it.createdDate
            )
        }
    }
}

data class PublicLetterProjection @QueryProjection constructor(
    val id: Long,
    val title: String,
    val fromAddress: String,
    val fromNickname: String,
    val paperColor: PaperColor,
    val stampId: Long,
    val createdDate: LocalDateTime
)

data class PublicLetterDetailProjection @QueryProjection constructor(
    val id: Long,
    val title: String,
    val content: String,
    val fromId: Long,
    val fromAddress: String,
    val fromNickname: String,
    val paperColor: PaperColor,
    val paperType: PaperType,
    val alignType: AlignType,
    val stampId: Long,
    val createdDate: LocalDateTime
)
