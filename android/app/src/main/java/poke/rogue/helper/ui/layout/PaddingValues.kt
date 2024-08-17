package poke.rogue.helper.ui.layout

import android.view.View
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.DP
import poke.rogue.helper.presentation.util.view.dp

data class PaddingValues(
    @Dimension(DP) val start: Int = 0.dp,
    @Dimension(DP) val top: Int = 0.dp,
    @Dimension(DP) val end: Int = 0.dp,
    @Dimension(DP) val bottom: Int = 0.dp
) {
    constructor(horizontal: Int = 0.dp, vertical: Int = 0.dp) : this(
        horizontal, vertical, horizontal, vertical
    )

    constructor(all: Int = 0.dp) : this(all, all, all, all)

    init {
        require(start >= 0) { "start padding can't be negative" }
        require(top >= 0) { "top padding can't be negative" }
        require(end >= 0) { "end padding can't be negative" }
        require(bottom >= 0) { "bottom padding can't be negative" }
    }
}

fun PaddingValues.applyTo(view: View) {
    view.setPadding(start, top, end, bottom)
}