package poke.rogue.helper.presentation.util

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan

/**
 * 잦은 체이닝을 사용한다면 SpannableString 의 확장 함수를 사용하세요
 */
fun SpannedString.drawable(
    iconDrawable: Drawable,
    iconSize: Int,
    delimiter: String = "|",
): SpannedString {
    val spannable = SpannableString(this)
    val iconIndex = indexOf(delimiter)
    if (iconIndex != -1) {
        iconDrawable.setBounds(0, 0, iconSize, iconSize)
        spannable.setSpan(
            ImageSpan(iconDrawable, ImageSpan.ALIGN_BOTTOM),
            iconIndex,
            iconIndex + 1,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
    return SpannedString(spannable)
}

fun SpannedString.style(
    targetWord: String,
    styleSpan: StyleSpan,
): SpannedString {
    val spannable = SpannableString(this)
    val startIndex = indexOf(targetWord)
    if (startIndex != -1) {
        spannable.setSpan(
            styleSpan,
            startIndex,
            startIndex + targetWord.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
    return SpannedString(spannable)
}

fun SpannedString.color(
    targetWord: String,
    color: Int,
): SpannedString {
    val spannable = SpannableString(this)
    val startIndex = indexOf(targetWord)
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

fun SpannableString.drawable(
    iconDrawable: Drawable,
    iconSize: Int,
    delimiter: String = "|",
): SpannableString {
    val iconIndex = indexOf(delimiter)
    if (iconIndex != -1) {
        val drawable =
            iconDrawable.apply {
                setBounds(0, 0, iconSize, iconSize)
            }
        val imageSpan = ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM)
        setSpan(imageSpan, iconIndex, iconIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return this
}

fun SpannableString.style(
    targetWord: String,
    styleSpan: StyleSpan,
): SpannableString {
    val startIndex = indexOf(targetWord)
    if (startIndex != -1) {
        this.setSpan(
            styleSpan,
            startIndex,
            startIndex + targetWord.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
    return this
}

fun SpannableString.color(
    targetWord: String,
    color: Int,
): SpannableString {
    val startIndex = indexOf(targetWord)
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
