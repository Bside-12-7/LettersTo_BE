package com.letters.to.member.ui

import com.letters.to.auth.application.TokenResponse
import com.letters.to.member.application.MemberRegisterRequest
import com.letters.to.member.application.MemberRegisterService
import com.letters.to.member.domain.Nickname
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/members")
class MemberController(private val memberRegisterService: MemberRegisterService) {

    @PostMapping
    fun register(@Valid @RequestBody request: MemberRegisterRequest): TokenResponse {
        return memberRegisterService.register(request)
    }

    @GetMapping("/nickname/exists")
    fun existsByNickname(@RequestParam nickname: Nickname): Boolean {
        return memberRegisterService.existsByNickname(nickname)
    }
}
