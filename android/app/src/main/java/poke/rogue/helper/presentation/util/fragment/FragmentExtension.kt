package poke.rogue.helper.presentation.util.fragment

import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar

fun Fragment.toast(message: String) {
    if (!isAdded) return
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(
    @StringRes messageRes: Int,
) {
    if (!isAdded) return
    Toast.makeText(requireContext(), messageRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(message: String) {
    if (!isAdded) return
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Fragment.longToast(
    @StringRes messageRes: Int,
) {
    if (!isAdded) return
    Toast.makeText(requireContext(), messageRes, Toast.LENGTH_LONG).show()
}

fun Fragment.snackBar(
    anchorView: View,
    message: () -> String,
) {
    if (!isAdded) return
    Snackbar.make(anchorView, message(), Snackbar.LENGTH_SHORT).show()
}

fun Fragment.snackBar(
    anchorView: View,
    @StringRes messageRes: Int,
) {
    if (!isAdded) return
    Snackbar.make(anchorView, messageRes, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.stringOf(
    @StringRes resId: Int,
    formatArgs: Any? = null,
) = getString(resId, formatArgs)

fun Fragment.colorOf(
    @ColorRes resId: Int,
) = ContextCompat.getColor(requireContext(), resId)

fun Fragment.drawableOf(
    @DrawableRes resId: Int,
) = ContextCompat.getDrawable(requireContext(), resId)

val Fragment.viewLifeCycle
    get() = viewLifecycleOwner.lifecycle

val Fragment.viewLifeCycleScope
    get() = viewLifecycleOwner.lifecycleScope
