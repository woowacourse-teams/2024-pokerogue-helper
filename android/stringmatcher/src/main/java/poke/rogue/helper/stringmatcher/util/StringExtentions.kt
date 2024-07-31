package poke.rogue.helper.stringmatcher.util

/**
 * 공백 제거, 특수문자 제거, lowerCase
 * */
internal fun String.clean(): String =
    this.replace("\\s".toRegex(), "")
        .replace("[^a-zA-Z0-9ㄱ-ㅎ가-힣]".toRegex(), "")
        .lowercase()
