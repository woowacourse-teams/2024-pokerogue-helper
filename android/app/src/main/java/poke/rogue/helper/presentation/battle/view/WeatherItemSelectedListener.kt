package poke.rogue.helper.presentation.battle.view

import android.view.View
import android.widget.AdapterView

inline fun <reified T> itemSelectListener(crossinline onSelected: (T) -> Unit): AdapterView.OnItemSelectedListener {
    var isSpinnerInitialized = false
    return object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long,
        ) {
            if (isSpinnerInitialized) {
                val selectedData = parent?.getItemAtPosition(position)
                val castedData =
                    requireNotNull(selectedData as? T) { "Selected data is not a ${T::class.simpleName}" }
                onSelected(castedData)
            } else {
                isSpinnerInitialized = true
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}