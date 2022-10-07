package com.letters.to.member.application

import com.letters.to.member.domain.Address
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Size

data class MemberRegisterRequest(
    val registerToken: String,

    @field:Length(min = 3, max = 10)
    val nickname: String,

    val address: Address,

    @field:Size(min = 1, max = 10)
    val topicIds: Set<Long>,

    @field:Size(min = 1, max = 9)
    val personalityIds: Set<Long>
)
