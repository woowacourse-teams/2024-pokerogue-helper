package poke.rogue.helper.presentation.dex.detail.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs


class BehaviorBlackJin(private val mContext: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<ImageView>(mContext, attrs) {
    private val marginTop: Float
    private val marginTopAfter: Float

    private val getYMax =
        mContext.resources.getDimension(poke.rogue.helper.R.dimen.appbar_height) - mContext.resources.getDimension(poke.rogue.helper.R.dimen.toolbar_height)

    init {
        marginTop = dpToPx(getYMax / 4)
        marginTopAfter = dpToPx(getYMax / 8)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: ImageView, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    /**
     * dependency 의 View 의 변화가 있을때 이벤트가 들어옵니다.
     */
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: ImageView, dependency: View): Boolean {
        child.alpha = getRatioValue(
            1f, 0f,
            abs(dependency.y.toDouble()).toFloat(), getYMax
        )
        child.alpha = getRatioValue(
            1f, 0f,
            abs(dependency.y.toDouble()).toFloat(), getYMax
        )

        child.x = (dependency.width / 2 - (child.width / 2)).toFloat()

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