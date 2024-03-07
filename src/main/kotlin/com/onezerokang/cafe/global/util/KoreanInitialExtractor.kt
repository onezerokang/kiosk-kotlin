package com.onezerokang.cafe.global.util


object KoreanInitialExtractor {
    private val CONSONANTS = charArrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )

    fun extract(s: String): String {
        val builder = StringBuilder()
        for (ch in s.toCharArray()) {
            if (ch in '가'..'힣') {
                val code = (ch.code - '가'.code) / 28 / 21
                builder.append(CONSONANTS[code])
                continue
            }
            builder.append(ch)
        }
        return builder.toString()
    }
}