package poke.rogue.helper.presentation.util

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan

fun SpannableString.drawable(
    fullText: String,
    iconDrawable: Drawable?,
    iconSize: Int,
) {
    val iconIndex = fullText.indexOf("|")
    if (iconIndex != -1) {
        val drawable =
            iconDrawable?.apply {
                setBounds(0, 0, iconSize, iconSize)
            }
        val imageSpan = ImageSpan(drawable!!, ImageSpan.ALIGN_BOTTOM)
        setSpan(imageSpan, iconIndex, iconIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

fun SpannableString.color(
    targetWord: String,
    color: Int,
    fullText: String,
): SpannableString {
    val startIndex = fullText.indexOf(targetWord)
    if (startIndex != -1) {
        this.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            startIndex + targetWord.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
    return this
}

fun SpannableString.style(
    targetWord: String,
    fullText: String,
): SpannableString {
    val startIndex = fullText.indexOf(targetWord)
    if (startIndex != -1) {
        this.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            startIndex + targetWord.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
    return this
}
