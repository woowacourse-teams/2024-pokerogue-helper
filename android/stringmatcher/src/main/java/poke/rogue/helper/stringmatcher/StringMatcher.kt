package poke.rogue.helper.stringmatcher

fun interface StringMatcher {
    fun isMatched(search: String, target: String): Boolean
}

fun String.has(search: String, matcher: StringMatcher = KrStringMatcher): Boolean {
    return matcher.isMatched(search, this)
}