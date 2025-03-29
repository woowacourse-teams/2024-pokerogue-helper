package poke.rogue.helper.presentation.util.view

import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar

class ProgressBarAnimation(
    private val progressBar: ProgressBar,
    private val from: Float,
    private val to: Float,
) : Animation() {
    override fun applyTransformation(
        interpolatedTime: Float,
        t: Transformation?,
    ) {
        super.applyTransformation(interpolatedTime, t)
        val value = from + to * interpolatedTime
        progressBar.progress = value.toInt()
    }
}

fun ProgressBar.animation(
    from: Float,
    to: Float,
): ProgressBarAnimation = ProgressBarAnimation(this, from, to)
