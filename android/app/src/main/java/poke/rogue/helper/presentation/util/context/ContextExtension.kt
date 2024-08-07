package poke.rogue.helper.presentation.util.context

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(
    @StringRes messageRes: Int,
) {
    Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.longToast(
    @StringRes messageRes: Int,
) {
    Toast.makeText(this, messageRes, Toast.LENGTH_LONG).show()
}

fun Context.snackBar(
    anchorView: View,
    message: () -> String,
) {
    Snackbar.make(anchorView, message(), Snackbar.LENGTH_SHORT).show()
}

fun Context.snackBar(
    anchorView: View,
    @StringRes messageRes: Int,
) {
    Snackbar.make(anchorView, messageRes, Snackbar.LENGTH_SHORT).show()
}

fun Context.stringOf(
    @StringRes resId: Int,
) = getString(resId)

fun Context.colorOf(
    @ColorRes resId: Int,
) = ContextCompat.getColor(this, resId)

fun Context.drawableOf(
    @DrawableRes resId: Int,
) = ContextCompat.getDrawable(this, resId)

fun Context.dialogWidthPercent(
    dialog: Dialog?,
    percent: Double = 0.8,
) {
    val deviceSize = deviceSize()
    dialog?.window?.run {
        val params = attributes
        params.width = (deviceSize[0] * percent).toInt()
        attributes = params
    }
}

fun Context.deviceSize(): IntArray {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager.currentWindowMetrics
        val windowInsets = windowMetrics.windowInsets

        val insets =
            windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout(),
            )
        val insetsWidth = insets.right + insets.left
        val insetsHeight = insets.top + insets.bottom

        val bounds = windowMetrics.bounds

        return intArrayOf(bounds.width() - insetsWidth, bounds.height() - insetsHeight)
    }
    val display = windowManager.defaultDisplay
    val size = Point()
    display?.getSize(size)
    return intArrayOf(size.x, size.y)
}

inline fun <reified T : Activity> Context.startActivity(argusBuilder: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply(argusBuilder))
}

fun Context.isNetworkConnected(): Boolean {
    var isConnected = false
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
    if (capabilities != null) {
        isConnected =
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )
    }
    return isConnected
}