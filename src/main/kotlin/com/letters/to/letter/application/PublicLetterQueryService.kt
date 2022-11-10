package com.letters.to.letter.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PublicLetterQueryService(private val publicLetterExtendsRepository: PublicLetterExtendsRepository) {
    fun findMoreBy(cursor: Long?): PublicLetterMoreResponse {
        val publicLetters = publicLetterExtendsRepository.findMoreBy(cursor)

        return PublicLetterMoreResponse(
            content = publicLetters,
            cursor = publicLetters.lastOrNull()?.id ?: cursor
        )
    }

    fun findOneBy(id: Long): LetterDetailResponse {
        return publicLetterExtendsRepository.findOneBy(id) ?: throw NoSuchElementException("유효한 공개편지가 아닙니다.")
    }
}
