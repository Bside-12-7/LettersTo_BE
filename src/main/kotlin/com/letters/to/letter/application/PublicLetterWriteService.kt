package com.letters.to.letter.application

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.letter.domain.Content
import com.letters.to.letter.domain.DeliveryLetter
import com.letters.to.letter.domain.DeliveryLetterRepository
import com.letters.to.letter.domain.DeliveryType
import com.letters.to.letter.domain.LetterBox
import com.letters.to.letter.domain.LetterBoxRepository
import com.letters.to.letter.domain.Picture
import com.letters.to.letter.domain.PublicLetter
import com.letters.to.letter.domain.PublicLetterRepository
import com.letters.to.letter.domain.Title
import com.letters.to.member.domain.MemberRepository
import com.letters.to.member.domain.findByIdAndActive
import com.letters.to.personality.domain.PersonalityRepository
import com.letters.to.stamp.domain.StampRepository
import com.letters.to.topic.domain.TopicRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PublicLetterWriteService(
    private val memberRepository: MemberRepository,
    private val stampRepository: StampRepository,
    private val topicRepository: TopicRepository,
    private val personalityRepository: PersonalityRepository,
    private val publicLetterRepository: PublicLetterRepository,
    private val letterBoxRepository: LetterBoxRepository,
    private val deliveryLetterRepository: DeliveryLetterRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun write(accessTokenPayload: AccessTokenPayload, request: PublicLetterWriteRequest) {
        val member = memberRepository.findByIdAndActive(accessTokenPayload.memberId)
            ?: throw NoSuchElementException("유효하지 않은 회원입니다.")
        val stamp = stampRepository.findByIdOrNull(request.stampId) ?: throw NoSuchElementException("유효하지 않은 우표입니다.")
        val topics = topicRepository.findAllById(request.topics)
        val personalities = personalityRepository.findAllById(request.personalities)

        val publicLetter = PublicLetter(
            title = Title(request.title),
            content = Content(request.content),
            paperType = request.paperType,
            paperColor = request.paperColor,
            alignType = request.alignType,
            stamp = stamp,
            topics = topics,
            personalities = personalities,
            fromMember = member
        )

        publicLetter.attachPictures(request.files.map { Picture(fileId = it) })

        member.useStamp()

        publicLetterRepository.save(publicLetter)

        applicationEventPublisher.publishEvent(
            LetterWriteEvent(
                id = publicLetter.id,
                files = request.files
            )
        )
    }

    fun reply(accessTokenPayload: AccessTokenPayload, request: DeliveryLetterWriteRequest) {
        val publicLetter =
            publicLetterRepository.findByIdOrNull(request.id) ?: throw NoSuchElementException("유효한 편지가 아닙니다.")
        val member = memberRepository.findByIdAndActive(accessTokenPayload.memberId)
            ?: throw NoSuchElementException("유효한 회원이 아닙니다.")
        val stamp = stampRepository.findByIdOrNull(request.stampId) ?: throw NoSuchElementException("유효하지 않은 우표입니다.")

        if (letterBoxRepository.existsByToMemberAndFromMember(member, publicLetter.fromMember)) {
            throw IllegalArgumentException("이미 주고받은 편지가 있습니다.")
        }

        val publicLetterToDeliveryLetter = DeliveryLetter(
            title = publicLetter.title,
            content = publicLetter.content,
            paperType = publicLetter.paperType,
            paperColor = publicLetter.paperColor,
            alignType = publicLetter.alignType,
            stamp = publicLetter.stamp,
            deliveryType = DeliveryType.NONE,
            fromMember = publicLetter.fromMember
        )

        publicLetterToDeliveryLetter.read = true
        publicLetterToDeliveryLetter.attachPictures(publicLetter.pictures.map { Picture(fileId = it.fileId) })
        publicLetterToDeliveryLetter.sendTo(member)
        publicLetterToDeliveryLetter.reply()

        val deliveryLetter = DeliveryLetter(
            title = Title(request.title),
            content = Content(request.content),
            paperType = request.paperType,
            paperColor = request.paperColor,
            alignType = request.alignType,
            stamp = stamp,
            deliveryType = request.deliveryType,
            fromMember = member
        )

        deliveryLetter.attachPictures(request.files.map { Picture(fileId = it) })
        deliveryLetter.sendTo(publicLetter.fromMember)

        letterBoxRepository.saveAll(
            listOf(
                LetterBox(
                    fromMember = publicLetter.fromMember,
                    toMember = member,
                    createdDate = deliveryLetter.createdDate
                ),
                LetterBox(
                    fromMember = member,
                    toMember = publicLetter.fromMember,
                    createdDate = deliveryLetter.createdDate
                )
            )
        )

        deliveryLetterRepository.saveAll(listOf(publicLetterToDeliveryLetter, deliveryLetter))

        applicationEventPublisher.publishEvent(
            LetterWriteEvent(
                id = deliveryLetter.id,
                delivered = true,
                fromMember = deliveryLetter.fromMember.nickname.value,
                toMemberId = deliveryLetter.toMember.id,
                content = deliveryLetter.title.value,
                files = request.files
            )
        )
    }
}
