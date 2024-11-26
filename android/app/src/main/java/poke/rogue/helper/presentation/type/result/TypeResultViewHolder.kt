package poke.rogue.helper.presentation.type.result

import android.content.Context
import android.text.SpannableString
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemTypeResultBinding
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.util.applySpans
import poke.rogue.helper.presentation.util.color
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.context.drawableOf
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.drawable
import poke.rogue.helper.presentation.util.style

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

        val (result, textView) =
            if (isMyType) {
                Pair(
                    context.stringOf(
                        resId = R.string.type_my_type_result,
                        formatArgs =
                            arrayOf(
                                typeName,
                                matchedResultText,
                            ),
                    ),
                    binding.tvMyTypeResult,
                )
            } else {
                Pair(
                    context.stringOf(
                        resId = R.string.type_opponent_type_result,
                        formatArgs =
                            arrayOf(
                                typeName,
                                matchedResultText,
                            ),
                    ),
                    binding.tvOpponentTypeResult,
                )
            }

        styleText(
            textView = textView,
            fullText = result,
            iconRes = iconResource,
            colorTargetWord = matchedResultText,
            fontStyleTargetWord = typeName,
            color = matchedResultTextColor,
        )
    }

    private fun styleText(
        textView: TextView,
        fullText: String,
        iconRes: Int,
        colorTargetWord: String,
        fontStyleTargetWord: String,
        color: Int,
    ) {
        val spannableString =
            SpannableString(fullText).applySpans {
                drawable(
                    iconDrawable = textView.context.drawableOf(iconRes),
                    iconSize = (textView.textSize * 1.2).toInt(),
                    fullText = fullText,
                )
                color(colorTargetWord, color, fullText)
                style(fontStyleTargetWord, fullText)
            }
        textView.text = spannableString
    }
}
