package poke.rogue.helper.presentation.util

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.Spannable.SPAN_INCLUSIVE_EXCLUSIVE
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import androidx.annotation.ColorInt

inline fun buildSpannedString(
    charSequence: CharSequence,
    builderAction: SpannableStringBuilder.() -> Unit,
): SpannedString {
    val builder = SpannableStringBuilder(charSequence)
    builder.builderAction()
    return SpannedString(builder)
}

fun SpannableStringBuilder.addIcon(
    fullText: String,
    targetDelimiter: String,
    iconDrawable: Drawable,
    bounds: Rect,
) {
    val delimiterIndex = fullText.indexOf(targetDelimiter)
    setSpan(
        ImageSpan(iconDrawable.apply { setBounds(bounds) }, ImageSpan.ALIGN_BOTTOM),
        delimiterIndex,
        delimiterIndex + 1,
        SPAN_INCLUSIVE_EXCLUSIVE,
    )
}

fun SpannableStringBuilder.applyFontStyle(
    fullText: String,
    target: String,
    styleSpan: StyleSpan,
) {
    val typeNameStartIndex = fullText.indexOf(target)
    if (typeNameStartIndex.isValid()) {
        setSpan(
            styleSpan,
            typeNameStartIndex,
            typeNameStartIndex + target.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
}

fun SpannableStringBuilder.applyColor(
    fullText: String,
    target: String,
    @ColorInt color: Int,
) {
    val matchedResultStartIndex = fullText.indexOf(target)
    if (matchedResultStartIndex.isValid()) {
        setSpan(
            ForegroundColorSpan(color),
            matchedResultStartIndex,
            matchedResultStartIndex + target.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
}

private fun Int.isValid() = this != -1
