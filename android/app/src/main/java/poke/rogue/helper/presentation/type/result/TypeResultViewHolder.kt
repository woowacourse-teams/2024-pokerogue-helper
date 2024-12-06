package poke.rogue.helper.presentation.type.result

import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannedString
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemTypeResultBinding
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.util.addIcon
import poke.rogue.helper.presentation.util.applyColor
import poke.rogue.helper.presentation.util.applyFontStyle
import poke.rogue.helper.presentation.util.buildSpannedString
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.context.drawableOf
import poke.rogue.helper.presentation.util.context.stringOf

class TypeResultViewHolder(private val binding: ItemTypeResultBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(typeMatchedResult: MatchedTypesUiModel) {
        bindTypeResult(typeMatchedResult)
        binding.typeResult = typeMatchedResult
        binding.rvResultMatchedTypes.adapter = TypeResultItemAdapter(typeMatchedResult.matchedItem)
    }

    private fun bindTypeResult(typeMatchedResult: MatchedTypesUiModel) {
        val matchedTypeResource = matchedTypeResource(typeMatchedResult)
        val typeResultView =
            matchedTypeResource.typeResultView(
                isMyType = typeMatchedResult.isMyType,
                myTypeView = binding.tvMyTypeResult,
                opponentTypeView = binding.tvOpponentTypeResult,
            )

        typeResultView.textView.text = bindTypeResultView(typeResultView, matchedTypeResource)
    }

    private fun matchedTypeResource(typeMatchedResult: MatchedTypesUiModel): MatchedTypeResource =
        with(binding.root.context) {
            MatchedTypeResource(
                typeName = stringOf(typeMatchedResult.selectedType.typeName),
                matchedResult = stringOf(typeMatchedResult.matchedResultUi.descriptionRes),
                matchedResultColor = colorOf(typeMatchedResult.matchedResultUi.colorRes),
                iconDrawable =
                    drawableOf(typeMatchedResult.selectedType.typeIconResId)
                        ?: error("해당하는 타입 아이콘이 없습니다."),
            )
        }

    private fun bindTypeResultView(
        typeResultView: TypeResultView,
        matchedTypeResource: MatchedTypeResource,
    ): SpannedString {
        val fullText = typeResultView.text
        val iconSIze = (typeResultView.textView.textSize * 1.2).toInt()
        return buildSpannedString(fullText) {
            addIcon(
                fullText = fullText,
                targetDelimiter = "|",
                iconDrawable = matchedTypeResource.iconDrawable,
                bounds = Rect(0, 0, iconSIze, iconSIze),
            )
            applyFontStyle(
                fullText = fullText,
                target = matchedTypeResource.typeName,
                styleSpan = StyleSpan(Typeface.BOLD),
            )
            applyColor(
                fullText = fullText,
                target = matchedTypeResource.matchedResult,
                color = matchedTypeResource.matchedResultColor,
            )
        }
    }
}

private data class MatchedTypeResource(
    val typeName: String,
    val matchedResult: String,
    @ColorInt val matchedResultColor: Int,
    val iconDrawable: Drawable,
) {
    fun typeResultView(
        isMyType: Boolean,
        myTypeView: TextView,
        opponentTypeView: TextView,
    ): TypeResultView {
        if (isMyType) return myType(myTypeView)
        return opponentType(opponentTypeView)
    }

    private fun myType(view: TextView): TypeResultView =
        TypeResultView(
            text =
                view.context.stringOf(
                    resId = R.string.type_my_type_result,
                    formatArgs =
                        arrayOf(
                            typeName,
                            matchedResult,
                        ),
                ),
            textView = view,
        )

    private fun opponentType(view: TextView): TypeResultView =
        TypeResultView(
            text =
                view.context.stringOf(
                    resId = R.string.type_opponent_type_result,
                    formatArgs =
                        arrayOf(
                            typeName,
                            matchedResult,
                        ),
                ),
            textView = view,
        )
}

private data class TypeResultView(
    val text: String,
    val textView: TextView,
)
