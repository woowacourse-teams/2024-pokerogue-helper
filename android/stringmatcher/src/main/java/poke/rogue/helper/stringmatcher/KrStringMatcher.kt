package poke.rogue.helper.stringmatcher

import poke.rogue.helper.stringmatcher.util.clean

internal object KrStringMatcher : StringMatcher {
    override fun isMatched(
        search: String,
        target: String,
    ): Boolean {
        val cleanedSearch = search.clean()
        val cleanedTarget = target.clean()
        val diff = cleanedTarget.length - cleanedSearch.length
        if (diff < 0) return false // 검색어가 더 길면 false를 리턴한다.

        for (i in 0..diff) {
            if (isSubstring(cleanedSearch, cleanedTarget, i)) return true
        }

        return false
    }

    /**
     * 1) searchChr == targetChr
     * 2) searchChr != targetChr && isKrConsonantMatch(searchChr, targetChr)
     *      - 한글 자음이 일치하는 경우
     *      - ex) search: "ㄱ", target: "가"
     * 위의 조건을 만족하면 loop를 돈다
     * */
    private fun isSubstring(
        search: String,
        target: String,
        startIndex: Int,
    ): Boolean {
        var subStringLen = 0
        val searchLen = search.length

        while (subStringLen < searchLen) {
            val searchChr = search[subStringLen]
            val targetChr = target[startIndex + subStringLen]
            if (searchChr != targetChr) {
                if (!isKrConsonantMatch(searchChr, targetChr)) return false
            }
            subStringLen++
        }
        return true
    }

    private fun isKrConsonantMatch(
        searchChr: Char,
        targetChr: Char,
    ): Boolean = searchChr.isKrConsonant() && targetChr.isKorean() && (searchChr == targetChr.mapToKrConsonant())

    private fun Char.isKrConsonant(): Boolean = this in KR_CONSONANT_LIST

    private fun Char.mapToKrConsonant(): Char {
        val consonantIndex = (this.code - KR_START_UNICODE) / KR_CONSONANT_UNIT
        return KR_CONSONANT_LIST[consonantIndex]
    }

    private fun Char.isKorean(): Boolean = this.code in KR_START_UNICODE..KR_END_UNICODE

    private const val KR_START_UNICODE = 0xAC00 // 가 code
    private const val KR_END_UNICODE = 0xD7A3 // 힣 code
    private const val KR_CONSONANT_UNIT = 588 // 각자음 마다 가지는 글자수
    private val KR_CONSONANT_LIST: List<Char> =
        listOf(
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ',
            'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
            'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ',
            'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ',
        )
}
