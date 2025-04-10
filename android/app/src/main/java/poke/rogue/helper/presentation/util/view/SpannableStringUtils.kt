package poke.rogue.helper.presentation.util.view

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import androidx.annotation.StyleRes
import androidx.core.text.inSpans

inline fun SpannableStringBuilder.font(
    typeface: Typeface? = null,
    builderAction: SpannableStringBuilder.() -> Unit,
) = inSpans(StyleSpan(typeface?.style ?: Typeface.DEFAULT.style), builderAction = builderAction)

inline fun SpannableStringBuilder.textAppearance(
    context: Context,
    @StyleRes style: Int,
    builderAction: SpannableStringBuilder.() -> Unit,
) = inSpans(TextAppearanceSpan(context, style), builderAction = builderAction)
