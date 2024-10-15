package poke.rogue.helper.presentation.dex.detail.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import poke.rogue.helper.R
import kotlin.math.abs

// BehaviorTopBall 에서 가져옴
class BehaviorPokemonTypes(context: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<LinearLayout>(context, attrs) {

    private val mContext: Context = context

    private val iconMarginStart: Float
    private val iconMarginTop: Float
    private val iconMarginTopAfter: Float

    private val yMax: Float

    init {
        yMax =
            context.resources.getDimension(R.dimen.appbar_height) - context.resources.getDimension(R.dimen.toolbar_height)

        iconMarginStart = dpToPx(30f)
        iconMarginTop = dpToPx(72f)
        iconMarginTopAfter = dpToPx(20f)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    /**
     * Called when there is a change in the dependency's view.
     */
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {
        // child icon scale [1, 0.8]
        child.scaleX = ratioValue(1f, 0.8f, abs(dependency.y), yMax)
        child.scaleY = ratioValue(1f, 0.8f, abs(dependency.y), yMax)

        // child icon set x [iconMarginLeft, (dependency.getWidth() - child.getWidth())/2]
        child.x = ratioValue(iconMarginStart, (dependency.width - child.width) / 2f, abs(dependency.y), yMax)

        // child icon set y [iconMarginTop, iconMarginTopAfter]
        child.y = ratioValue(iconMarginTop, iconMarginTopAfter, abs(dependency.y), yMax)

        return true
    }

    private fun dpToPx(dp: Float): Float {
        val dm = mContext.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm)
    }

    private fun ratioValue(firstValue: Float, lastValue: Float, getY: Float, getYMax: Float): Float {
        val temp = -(firstValue - lastValue) * getY / getYMax
        return firstValue + temp
    }
}
