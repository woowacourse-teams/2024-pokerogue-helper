package poke.rogue.helper.presentation.dex.detail.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import poke.rogue.helper.R
import kotlin.math.abs

class BehaviorToolbarMove(context: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<LinearLayout>(context, attrs) {

    private var initialY: Float = 0f
    private var toolbarY: Float = 0f
    private var initialX: Float = 0f
    private var toolbarX: Float = 0f
    private var startScale = 1f
    private var endScale = 0.5f  // Scale down to half the size

    override fun layoutDependsOn(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {
        // `AppBarLayout`에 종속되도록 설정
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {
        if (initialY == 0f) {
            // 초기 Y 위치 저장
            initialY = child.y
            // Toolbar의 Y 위치를 계산
            toolbarY = parent.findViewById<View>(R.id.toolbar_pokemon_detail).y
        }

        if (initialX == 0f) {
            // 초기 X 위치 저장
            initialX = child.x
            // Toolbar의 X 위치를 계산
            toolbarX = (dependency.width - child.width) / 2f  // 중앙 정렬
        }

        val scrollProgress = abs(dependency.y) / ((dependency as AppBarLayout).totalScrollRange.toFloat())

        // Y 위치와 X 위치 계산 (LinearLayout을 Toolbar 쪽으로 이동)
        child.y = initialY - (initialY - toolbarY) * scrollProgress
        child.x = initialX - (initialX - toolbarX) * scrollProgress

        // 크기 조정 (Scale 변경)
        val scale = startScale - (startScale - endScale) * scrollProgress
        child.scaleX = scale
        child.scaleY = scale

        return true
    }
}
