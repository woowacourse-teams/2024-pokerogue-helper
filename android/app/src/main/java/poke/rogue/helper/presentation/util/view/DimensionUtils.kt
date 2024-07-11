package poke.rogue.helper.presentation.util.view

import android.content.res.Resources

val density = Resources.getSystem().displayMetrics.density

val Int.dp
    get(): Int = (density * this).toInt()

val Float.dp
    get(): Float = density * this

val Int.dpToPx
    get(): Int = (this / density).toInt()

val Float.dpToPx
    get(): Float = this / density
