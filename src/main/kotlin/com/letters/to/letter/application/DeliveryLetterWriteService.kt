package com.letters.to.letter.application

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.letter.domain.Content
import com.letters.to.letter.domain.DeliveryLetter
import com.letters.to.letter.domain.DeliveryLetterRepository
import com.letters.to.letter.domain.Picture
import com.letters.to.letter.domain.Title
import com.letters.to.member.domain.MemberRepository
import com.letters.to.member.domain.findByIdAndActive
import com.letters.to.stamp.domain.StampRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeliveryLetterWriteService(
    private val memberRepository: MemberRepository,
    private val stampRepository: StampRepository,
    private val deliveryLetterRepository: DeliveryLetterRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun write(accessTokenPayload: AccessTokenPayload, request: DeliveryLetterWriteRequest) {
        val targetDeliveryLetter =
            deliveryLetterRepository.findByIdOrNull(request.id) ?: throw NoSuchElementException("유효한 편지가 아닙니다.")
        val member = memberRepository.findByIdAndActive(accessTokenPayload.memberId)
            ?: throw NoSuchElementException("유효한 회원이 아닙니다.")
        val stamp = stampRepository.findByIdOrNull(request.stampId) ?: throw NoSuchElementException("유효하지 않은 우표입니다.")

        val deliveryLetter = DeliveryLetter(
            title = Title(request.title),
            content = Content(request.content),
            paperType = request.paperType,
            paperColor = request.paperColor,
            stamp = stamp,
            deliveryType = request.deliveryType,
            fromMember = member
        )

        deliveryLetter.attachPictures(request.files.map { Picture(fileId = it) })
        deliveryLetter.sendTo(targetDeliveryLetter.fromMember)

        deliveryLetterRepository.save(deliveryLetter)

        applicationEventPublisher.publishEvent(
            LetterWriteEvent(
                id = deliveryLetter.id,
                files = request.files
            )
        )
    }
}
