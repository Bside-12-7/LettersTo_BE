package com.letters.to.member.application

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Size

data class MemberRegisterRequest(
    val registerToken: String,

    @field:Length(min = 3, max = 10)
    val nickname: String,

    val geolocationId: Long,

    @field:Size(min = 1, max = 10)
    val topicIds: Set<Long>,

    @field:Size(min = 1, max = 12)
    val personalityIds: Set<Long>
)
