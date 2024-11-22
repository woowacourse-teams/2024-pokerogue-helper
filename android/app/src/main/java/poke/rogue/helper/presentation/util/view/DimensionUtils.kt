package poke.rogue.helper.presentation.util.view

import android.content.res.Resources

val density = Resources.getSystem().displayMetrics.density

// TODO : move to ui/unit/Dimension.kt

val Int.dp
    get(): Int = (density * this).toInt()

val Float.dp
    get(): Float = density * this

val Int.px
    get(): Int = (this / density).toInt()

val Float.px
    get(): Float = this / density
