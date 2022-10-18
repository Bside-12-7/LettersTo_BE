package com.letters.to.member.application

import com.letters.to.member.domain.Member
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

data class MemberUpdateRequest(
    val nickname: String?,
    val geolocationId: Long?,
    val topicIds: Set<Long>?,
    val personalityIds: Set<Long>?
)

data class MemberResponse(
    val nickname: String,
    val parentGeolocationId: Long?,
    val geolocationId: Long,
    val topicIds: List<Long>,
    val personalityIds: List<Long>
) {
    constructor(member: Member) : this(
        nickname = member.nickname.value,
        parentGeolocationId = member.geolocation.parent?.id,
        geolocationId = member.geolocation.id,
        topicIds = member.topics.map { it.id },
        personalityIds = member.personalities.map { it.id }
    )
}
