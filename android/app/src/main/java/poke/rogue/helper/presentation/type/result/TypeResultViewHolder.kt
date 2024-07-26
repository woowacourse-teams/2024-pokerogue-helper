package poke.rogue.helper.presentation.type.result

import android.content.Context
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemTypeResultBinding
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.context.stringOf

class TypeResultViewHolder(private val binding: ItemTypeResultBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(typeMatchedResult: MatchedTypesUiModel) {
        val context = binding.root.context

        bindTypeNameText(typeMatchedResult, context)
        bindTypeStrengthText(typeMatchedResult, context)

        binding.typeResult = typeMatchedResult
        binding.rvResultMatchedTypes.layoutManager = GridLayoutManager(context, 4)
        binding.rvResultMatchedTypes.adapter = TypeResultItemAdapter(typeMatchedResult.matchedItem)
    }

    private fun bindTypeNameText(
        typeMatchedResult: MatchedTypesUiModel,
        context: Context,
    ) {
        binding.tvResultSelectedTypeName.text =
            if (typeMatchedResult.isMyType) {
                context.getString(
                    R.string.type_item_result_subject_mine,
                    typeMatchedResult.selectedType.typeName,
                )
            } else {
                context.getString(
                    R.string.type_item_result_subject_opponent,
                    typeMatchedResult.selectedType.typeName,
                )
            }
    }

    private fun bindTypeStrengthText(
        typeMatchedResult: MatchedTypesUiModel,
        context: Context,
    ) {
        val matchedResultText = context.stringOf(typeMatchedResult.matchedResultDescriptionResId)
        val matchedResultTextTail = context.stringOf(R.string.type_item_result_tail)
        val matchedResultTextColor = context.colorOf(typeMatchedResult.matchedResultColorResId)

        binding.tvResultSelectedTypeStrength.text =
            buildSpannedString {
                inSpans(
                    ForegroundColorSpan(matchedResultTextColor),
                    StyleSpan(Typeface.BOLD),
                ) {
                    append(matchedResultText)
                }
                append(matchedResultTextTail)
            }
    }
}
