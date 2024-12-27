package poke.rogue.helper.presentation.battle

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("animatedVisibility")
fun View.setAnimatedVisibility(visible: Boolean) {
    if (visible) {
        animate().alpha(1f).setDuration(200).withStartAction {
            visibility = View.VISIBLE
        }.start()
    } else {
        animate().alpha(0f).setDuration(200).withEndAction {
            visibility = View.GONE
        }.start()
    }
}
