package poke.rogue.helper.presentation.type.mapper

import poke.rogue.helper.R
import poke.rogue.helper.data.model.MatchedResult

fun MatchedResult.displayName(): String {
    return when (this) {
        MatchedResult.STRONG -> "강한"
        MatchedResult.WEAK -> "약한"
        MatchedResult.INEFFECTIVE -> "무효한"
        MatchedResult.NORMAL -> throw IllegalStateException("")
    }
}

fun MatchedResult.displayColor(): Int {
    return when (this) {
        MatchedResult.STRONG -> R.color.poke_red_20
        MatchedResult.WEAK -> R.color.poke_green_20
        MatchedResult.INEFFECTIVE -> R.color.poke_grey_80
        MatchedResult.NORMAL -> throw IllegalStateException("")
    }
}
