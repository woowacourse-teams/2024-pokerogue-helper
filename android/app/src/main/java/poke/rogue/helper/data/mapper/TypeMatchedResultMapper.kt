package poke.rogue.helper.data.mapper

import poke.rogue.helper.data.model.MatchedResult

fun MatchedResult.toString(): String {
    return when (this) {
        MatchedResult.STRONG -> "강한"
        MatchedResult.WEAK -> "약한"
        MatchedResult.INVALID -> "무효한"
        else -> ""
    }
}
