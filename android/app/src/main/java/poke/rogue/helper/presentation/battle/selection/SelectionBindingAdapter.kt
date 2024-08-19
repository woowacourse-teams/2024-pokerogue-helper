package poke.rogue.helper.presentation.battle.selection

import android.view.View
import androidx.databinding.BindingAdapter
import poke.rogue.helper.R

@BindingAdapter("invisible")
fun View.setInvisible(invisible: Boolean) {
    visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("selectedBackground")
fun View.setBackground(isSelected: Boolean) {
    if (isSelected) {
        setBackgroundResource(R.drawable.bg_battle_selected_border)
    } else {
        setBackgroundResource(R.drawable.bg_battle_default)
    }
}
