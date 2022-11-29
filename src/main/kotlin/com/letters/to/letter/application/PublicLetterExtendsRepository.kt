package com.letters.to.letter.application

interface PublicLetterExtendsRepository {
    fun findMoreBy(cursor: Long?): List<PublicLetterResponse>
    fun findOneBy(memberId: Long, id: Long): LetterDetailResponse?
}
