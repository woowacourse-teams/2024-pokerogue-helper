package poke.rogue.helper.presentation.util

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.SpannedString
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

fun SpannableStringBuilder.color(
    fullText: String,
    colorTargetWord: String,
    color: Int,
) {
    val colorStartIndex = fullText.indexOf(colorTargetWord)
    if (colorStartIndex != -1) {
        setSpan(
            ForegroundColorSpan(color),
            colorStartIndex,
            colorStartIndex + colorTargetWord.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
}

fun SpannableStringBuilder.style(
    fullText: String,
    fontStyleTargetWord: String,
) {
    val fontStyleStartIndex = fullText.indexOf(fontStyleTargetWord)
    if (fontStyleStartIndex != -1) {
        setSpan(
            StyleSpan(Typeface.BOLD),
            fontStyleStartIndex,
            fontStyleStartIndex + fontStyleTargetWord.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
}

fun SpannedString.drawable(
    iconDrawable: Drawable?,
    iconSize: Int,
    fullText: String,
): SpannedString {
    val spannable = SpannableString(this)
    val iconIndex = fullText.indexOf("|")
    if (iconIndex != -1) {
        iconDrawable?.setBounds(0, 0, iconSize, iconSize)
        spannable.setSpan(
            ImageSpan(iconDrawable!!, ImageSpan.ALIGN_BOTTOM),
            iconIndex,
            iconIndex + 1,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
    return SpannedString(spannable)
}

fun SpannedString.color(
    targetWord: String,
    color: Int,
    fullText: String,
): SpannedString {
    val spannable = SpannableString(this)
    val startIndex = fullText.indexOf(targetWord)
    if (startIndex != -1) {
        spannable.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            startIndex + targetWord.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
    return SpannedString(spannable)
}

fun SpannedString.style(
    targetWord: String,
    fullText: String,
): SpannedString {
    val spannable = SpannableString(this)
    val startIndex = fullText.indexOf(targetWord)
    if (startIndex != -1) {
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            startIndex + targetWord.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
    return SpannedString(spannable)
}
