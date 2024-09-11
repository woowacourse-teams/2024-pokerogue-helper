package poke.rogue.helper.presentation.util.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val itemColumn = itemPosition % spanCount

        if (!includeEdge) {
            outRect.left =
                spacing - itemColumn * spacing / spanCount
            outRect.right =
                (itemColumn + 1) * spacing / spanCount

            if (itemPosition < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = itemColumn * spacing / spanCount
            outRect.right =
                spacing - (itemColumn + 1) * spacing / spanCount
            if (itemPosition >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}
