package poke.rogue.helper.presentation.util.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
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

fun Fragment.stringOf(
    @StringRes resId: Int,
    vararg formatArgs: Any?,
) = getString(resId, *formatArgs)

fun Fragment.colorOf(
    @ColorRes resId: Int,
) = ContextCompat.getColor(requireContext(), resId)

fun Fragment.drawableOf(
    @DrawableRes resId: Int,
) = ContextCompat.getDrawable(requireContext(), resId)

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T {
    return this.apply {
        arguments = Bundle().apply(argsBuilder)
    }
}

val Fragment.viewLifeCycle
    get() = viewLifecycleOwner.lifecycle

val Fragment.viewLifeCycleScope
    get() = viewLifecycleOwner.lifecycleScope

inline fun <reified T : Activity> Fragment.startActivity(argusBuilder: Intent.() -> Unit) {
    startActivity(Intent(requireContext(), T::class.java).apply(argusBuilder))
}
