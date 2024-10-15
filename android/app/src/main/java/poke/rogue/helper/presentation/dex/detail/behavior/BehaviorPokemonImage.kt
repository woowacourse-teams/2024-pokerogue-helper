package poke.rogue.helper.presentation.dex.detail.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import poke.rogue.helper.R

class BehaviorPokemonImage(context: Context, attrs: AttributeSet?) : CoordinatorLayout.Behavior<ImageView>(context, attrs) {

    private val mContext: Context = context

    private val iconMarginLeft: Float
    private val iconMarginTop: Float
    private val iconMarginTopAfter: Float

    private val yMax: Float

    init {
//        getYMax =
//            context.resources.getDimension(R.dimen.appbar_height) - context.resources.getDimension(R.dimen.toolbar_height)
        yMax =
            context.resources.getDimension(R.dimen.appbar_height) - context.resources.getDimension(R.dimen.toolbar_height)

        iconMarginLeft = dpToPx(30f)
        iconMarginTop = dpToPx(72f)
        iconMarginTopAfter = dpToPx(20f)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: ImageView, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    /**
     * Called when there is a change in the dependency's view.
     */
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: ImageView, dependency: View): Boolean {
        // child icon scale [1, 0.8]
        child.scaleX = getRatioValue(1f, 0.8f, Math.abs(dependency.y), yMax)
        child.scaleY = getRatioValue(1f, 0.8f, Math.abs(dependency.y), yMax)

        // child icon set x [iconMarginLeft, (dependency.getWidth() - child.getWidth())/2]
        child.x = getRatioValue(iconMarginLeft, (dependency.width - child.width) / 2f, Math.abs(dependency.y), yMax)

        // child icon set y [iconMarginTop, iconMarginTopAfter]
        child.y = getRatioValue(iconMarginTop, iconMarginTopAfter, Math.abs(dependency.y), yMax)

        return false
    }

    private fun dpToPx(dp: Float): Float {
        val dm = mContext.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm)
    }

    private fun getRatioValue(firstValue: Float, lastValue: Float, getY: Float, getYMax: Float): Float {
        val temp = -(firstValue - lastValue) * getY / getYMax
        return firstValue + temp
    }
}
