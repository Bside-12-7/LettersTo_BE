package com.letters.to.letter.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row

class ContentTest : StringSpec({
    "편지 제목은 100~500자를 허용한다" {
        forAll(
            row("a".repeat(100)),
            row("a".repeat(1000))
        ) {
            shouldNotThrowAny { Content(it) }
        }
    }

    "편지 제목은 100자 미만 또는 500자를 초과할 수 없다" {
        forAll(
            row("a".repeat(99)),
            row("a".repeat(1001))
        ) {
            shouldThrowAny { Content(it) }
        }
    }
})
