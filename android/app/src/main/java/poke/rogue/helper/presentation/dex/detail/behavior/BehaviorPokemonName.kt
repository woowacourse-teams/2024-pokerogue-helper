package poke.rogue.helper.presentation.dex.detail.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import poke.rogue.helper.R
import kotlin.math.abs


class BehaviorPokemonName(private val mContext: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<TextView>(mContext, attrs) {
    private val marginLeft: Float
    private val marginTop: Float
    private val marginTopAfter: Float

    private val getYMax =
        mContext.resources.getDimension(R.dimen.appbar_height) - mContext.resources.getDimension(R.dimen.toolbar_height)

    init {
        marginLeft = dpToPx(70f)
        marginTop = dpToPx(70f)
        marginTopAfter = dpToPx(53f)
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    /**
     * dependency 의 View 의 변화가 있을때 이벤트가 들어옵니다.
     */
    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {
        child.scaleX = getRatioValue(
            1f, 0.8f,
            abs(dependency.y.toDouble()).toFloat(), getYMax
        )

        child.x = getRatioValue(
            marginLeft, ((dependency.width - child.width) / 2).toFloat(),
            abs(dependency.y.toDouble()).toFloat(), getYMax
        )

        child.y = getRatioValue(
            marginTop, marginTopAfter,
            abs(dependency.y.toDouble()).toFloat(), getYMax
        )

        return false
    }

    private fun dpToPx(dp: Float): Float {
        val dm = mContext.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm).toInt().toFloat()
    }

    private fun getRatioValue(firstValue: Float, lastValue: Float, getY: Float, getYMax: Float): Float {
        val temp = -(firstValue - lastValue) * getY / getYMax
        return firstValue + temp
    }
}