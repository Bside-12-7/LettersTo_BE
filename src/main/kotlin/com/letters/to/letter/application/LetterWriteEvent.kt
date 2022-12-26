package com.letters.to.letter.application

data class LetterWriteEvent(
    val id: Long,
    val delivered: Boolean = false,
    val fromMember: String = "",
    val toMemberId: Long = 0L,
    val content: String = "",
    val files: List<String>
)
