package poke.rogue.helper.presentation.util.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearSpacingItemDecoration(
    private val spacing: Int,
    private val includeEdge: Boolean = true,
    private val orientation: Orientation = Orientation.VERTICAL,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (orientation == Orientation.VERTICAL) {
            outRect.top = if (includeEdge || position != 0) spacing else 0
            outRect.bottom = spacing
            return
        }
        outRect.left = if (includeEdge || position != 0) spacing else 0
        outRect.right = spacing
    }

    enum class Orientation {
        HORIZONTAL,
        VERTICAL
    }
}