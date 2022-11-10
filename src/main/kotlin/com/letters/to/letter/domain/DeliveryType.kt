package com.letters.to.letter.domain

import com.letters.to.member.domain.Member
import java.time.LocalDateTime
import java.util.function.BiFunction

private const val UNIT = 2000

enum class DeliveryType(val stampCount: Int, private val delegate: BiFunction<Member, Member, LocalDateTime>) {
    NONE(0, { _, _ -> LocalDateTime.now() }),
    NORMAL(1,
        { from, to ->
            val meter = from.geolocation.haversine(to.geolocation)
            LocalDateTime.now().plusMinutes((meter / UNIT).toLong())
        }),
    EXPRESS(5,
        { _, _ -> LocalDateTime.now() });

    fun deliveryDate(fromMember: Member, toMember: Member): LocalDateTime {
        return delegate.apply(fromMember, toMember)
    }
}
