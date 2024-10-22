package poke.rogue.helper.presentation.battle.selection

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
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

@BindingAdapter("onTextChanged")
fun setOnTextChangedListener(
    editText: EditText,
    handler: QueryHandler,
) {
    editText.addTextChangedListener(
        object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int,
            ) {
                handler.queryName(s.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {}

            override fun afterTextChanged(s: Editable?) {}
        },
    )
}
