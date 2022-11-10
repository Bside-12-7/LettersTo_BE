package com.letters.to.letter.domain

@JvmInline
value class Content(val value: String) {
    init {
        require(value.length <= 500) { "내용은 500자를 초과할 수 없습니다." }
    }
}
