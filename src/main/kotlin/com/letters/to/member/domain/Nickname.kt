package com.letters.to.member.domain

private val REGEX = Regex("[A-Za-zㄱ-ㅎ가-힣0-9]+")

@JvmInline
value class Nickname(val value: String) {
    init {
        require(value.length in 3..10 && value.matches(REGEX)) { "닉네임은 3-10자 한/영/숫자 이어야 합니다." }
    }
}
