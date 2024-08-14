package poke.rogue.helper.presentation.util.activity

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val inputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let { view ->
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}

fun Activity.show() {
    val inputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let { view ->
        inputMethodManager.showSoftInput(view, 0)
    }
}