package poke.rogue.helper.ui.layout

import android.os.Parcelable
import android.view.View
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.DP
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.presentation.util.view.dp

@Parcelize
data class PaddingValues(
    @Dimension(DP) val start: Int = 0.dp,
    @Dimension(DP) val top: Int = 0.dp,
    @Dimension(DP) val end: Int = 0.dp,
    @Dimension(DP) val bottom: Int = 0.dp,
) : Parcelable {
    constructor(horizontal: Int, vertical: Int) : this(
        horizontal,
        vertical,
        horizontal,
        vertical,
    )

    constructor(all: Int) : this(all, all, all, all)

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
