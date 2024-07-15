package poke.rogue.helper.presentation.type.mapper

import poke.rogue.helper.data.model.MatchedResult

fun MatchedResult.displayName(): String {
    return when (this) {
        MatchedResult.STRONG -> "강한"
        MatchedResult.WEAK -> "약한"
        MatchedResult.INEFFECTIVE -> "무효한"
        MatchedResult.NORMAL -> "일반"
    }
}
