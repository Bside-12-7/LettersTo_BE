package com.letters.to.member.ui

import com.letters.to.auth.application.TokenResponse
import com.letters.to.auth.domain.AccessTokenPayload
import com.letters.to.member.application.MemberQueryService
import com.letters.to.member.application.MemberRegisterRequest
import com.letters.to.member.application.MemberRegisterService
import com.letters.to.member.application.MemberResponse
import com.letters.to.member.application.MemberUpdateRequest
import com.letters.to.member.application.MemberUpdateService
import com.letters.to.member.application.MemberWithdrawalService
import com.letters.to.member.domain.Nickname
import com.letters.to.web.AccessToken
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val memberRegisterService: MemberRegisterService,
    private val memberWithdrawalService: MemberWithdrawalService,
    private val memberUpdateService: MemberUpdateService,
    private val memberQueryService: MemberQueryService
) {

    @PostMapping
    fun register(@Valid @RequestBody request: MemberRegisterRequest): TokenResponse {
        return memberRegisterService.register(request)
    }

    @GetMapping("/nickname/exists")
    fun existsByNickname(@RequestParam nickname: Nickname): Boolean {
        return memberRegisterService.existsByNickname(nickname)
    }

    @PatchMapping
    fun update(@AccessToken accessToken: AccessTokenPayload, request: MemberUpdateRequest) {
        memberUpdateService.update(accessToken, request)
    }

    @GetMapping
    fun findAccessMember(@AccessToken accessToken: AccessTokenPayload): MemberResponse {
        return memberQueryService.findById(accessToken.memberId)
    }

    @DeleteMapping
    fun withdrawal(@AccessToken accessToken: AccessTokenPayload): ResponseEntity<Unit> {
        memberWithdrawalService.withdrawal(accessToken)

        return ResponseEntity.noContent().build()
    }
}
