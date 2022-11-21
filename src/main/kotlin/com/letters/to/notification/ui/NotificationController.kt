package com.letters.to.notification.ui

import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.notification.application.NotificationMoreRequest
import com.letters.to.notification.application.NotificationMoreResponse
import com.letters.to.notification.application.NotificationResponse
import com.letters.to.notification.application.NotificationService
import com.letters.to.web.AccessToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notifications")
class NotificationController(private val notificationService: NotificationService) {

    @GetMapping
    fun findMoreBy(
        @AccessToken accessTokenPayload: AccessTokenPayload,
        request: NotificationMoreRequest
    ): NotificationMoreResponse {
        return notificationService.findMoreBy(accessTokenPayload.memberId, request)
    }

    @PostMapping("/{id}/read")
    fun read(@AccessToken accessTokenPayload: AccessTokenPayload, @PathVariable id: Long): NotificationResponse {
        return notificationService.read(accessTokenPayload.memberId, id)
    }
}
