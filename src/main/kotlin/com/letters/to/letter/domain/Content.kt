package com.letters.to.letter.domain

@JvmInline
value class Content(val value: String) {
    init {
        require(value.length in (100..1000)) { "편지 내용 길이는 100~500자여야 합니다." }
    }
}
