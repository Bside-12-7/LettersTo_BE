package com.letters.to.letter.domain

@JvmInline
value class Title(val value: String) {
    init {
        require(value.length <= 30) { "제목은 30자를 초과할 수 없습니다." }
    }

    companion object {
        val UNTITLED = Title("무제")
    }
}
