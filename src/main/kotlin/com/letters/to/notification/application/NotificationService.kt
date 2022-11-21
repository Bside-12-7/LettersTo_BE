package com.letters.to.notification.application

import com.letters.to.notification.domain.NotificationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val notificationExtendsRepository: NotificationExtendsRepository
) {

    @Transactional
    fun read(memberId: Long, id: Long): NotificationResponse {
        val notification = notificationRepository.findByIdOrNull(id) ?: throw NoSuchElementException("유효한 알림이 아닙니다.")

        check(notification.memberId == memberId) { "읽기 권한이 없습니다." }

        notification.read = true

        return NotificationResponse(notification)
    }

    @Transactional(readOnly = true)
    fun findMoreBy(memberId: Long, request: NotificationMoreRequest): NotificationMoreResponse {
        val content =
            notificationExtendsRepository.findMoreBy(memberId = memberId, cursor = request.cursor, read = request.read)
                .map { NotificationResponse(it) }

        return NotificationMoreResponse(
            content = content,
            cursor = content.lastOrNull()?.id ?: request.cursor
        )
    }
}
