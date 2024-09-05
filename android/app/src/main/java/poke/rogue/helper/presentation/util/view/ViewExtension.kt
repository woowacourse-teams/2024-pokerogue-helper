package poke.rogue.helper.presentation.util.view

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.setOnSearchAction(action: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            action()
        }
        true
    }
}
