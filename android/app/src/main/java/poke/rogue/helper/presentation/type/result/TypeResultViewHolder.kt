package poke.rogue.helper.presentation.type.result

import android.content.Context
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemTypeResultBinding
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.context.stringOf

class TypeResultViewHolder(private val binding: ItemTypeResultBinding) : RecyclerView.ViewHolder(binding.root) {
    private val flexboxLayoutManager: FlexboxLayoutManager by lazy {
        FlexboxLayoutManager(binding.root.context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
    }

    fun bind(typeMatchedResult: MatchedTypesUiModel) {
        val context = binding.root.context

        bindTypeNameText(typeMatchedResult, context)
        bindTypeStrengthText(typeMatchedResult, context)

        binding.typeResult = typeMatchedResult
        binding.rvResultMatchedTypes.layoutManager = flexboxLayoutManager
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
        val matchedResultText = typeMatchedResult.matchedResult
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
