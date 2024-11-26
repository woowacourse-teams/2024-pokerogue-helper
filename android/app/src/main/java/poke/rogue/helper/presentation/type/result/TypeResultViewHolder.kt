package poke.rogue.helper.presentation.type.result

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemTypeResultBinding
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.context.drawableOf
import poke.rogue.helper.presentation.util.context.stringOf
import timber.log.Timber

class TypeResultViewHolder(private val binding: ItemTypeResultBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(typeMatchedResult: MatchedTypesUiModel) {
        val context = binding.root.context
        bindTypeResult(typeMatchedResult, context)
        binding.typeResult = typeMatchedResult
        binding.rvResultMatchedTypes.adapter = TypeResultItemAdapter(typeMatchedResult.matchedItem)
    }

    private fun bindTypeResult(
        typeMatchedResult: MatchedTypesUiModel,
        context: Context,
    ) {
        val isMyType = typeMatchedResult.isMyType
        val typeName = context.stringOf(typeMatchedResult.selectedType.typeName)
        val matchedResultText = context.stringOf(typeMatchedResult.matchedResultUi.descriptionRes)
        val matchedResultTextColor = context.colorOf(typeMatchedResult.matchedResultUi.colorRes)
        val iconResource = typeMatchedResult.selectedType.typeIconResId

        val (result, chip) = if (isMyType) {
            Pair(
                context.stringOf(
                    resId = R.string.type_my_type_result,
                    formatArgs = arrayOf(
                        typeName,
                        matchedResultText,
                    )
                ), binding.chipMyTypeResult
            )
        } else {
            Pair(
                context.stringOf(
                    resId = R.string.type_opponent_type_result,
                    formatArgs = arrayOf(
                        typeName,
                        matchedResultText,
                    )
                ), binding.chipOpponentTypeResult
            )
        }

        Timber.d("result: $result")

        styleText(
            chip = chip,
            fullText = result,
            iconRes = iconResource,
            colorTargetWord = matchedResultText,
            fontStyleTargetWord = typeName,
            color = matchedResultTextColor,
        )
    }

    private fun styleText(
        chip: TextView,
        fullText: String,
        iconRes: Int,
        colorTargetWord: String,
        fontStyleTargetWord: String,
        color: Int,
    ) {
        val spannableString = SpannableString(fullText).applySpans {
            val iconIndex = fullText.indexOf("|") // 아이콘 자리 표시자
            if (iconIndex != -1) {
                val drawable = binding.root.context.drawableOf(iconRes)?.apply {
                    val iconSize = (chip.textSize * 1.2).toInt() // 텍스트 크기의 1.5배
                    setBounds(0, 0, iconSize, iconSize)
                }
                val imageSpan = ImageSpan(drawable!!, ImageSpan.ALIGN_BOTTOM)
                setSpan(imageSpan, iconIndex, iconIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            applyColor(colorTargetWord, color, fullText)
            applyStyle(fontStyleTargetWord, fullText)
        }
        chip.text = spannableString
    }

}

inline fun SpannableString.applySpans(configure: SpannableString.() -> Unit): SpannableString {
    this.configure()
    return this
}

fun SpannableString.applyColor(
    targetWord: String,
    color: Int,
    fullText: String
): SpannableString {
    val startIndex = fullText.indexOf(targetWord)
    if (startIndex != -1) {
        this.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            startIndex + targetWord.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return this
}

fun SpannableString.applyStyle(
    targetWord: String,
    fullText: String
): SpannableString {
    val startIndex = fullText.indexOf(targetWord)
    if (startIndex != -1) {
        this.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            startIndex + targetWord.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return this
}

