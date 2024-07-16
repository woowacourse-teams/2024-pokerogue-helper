package poke.rogue.helper.presentation.type

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import poke.rogue.helper.databinding.ItemTypeResultBinding
import poke.rogue.helper.presentation.type.model.TypeMatchedResultUiModel
import poke.rogue.helper.presentation.util.context.colorOf

class TypeResultViewHolder(private val binding: ItemTypeResultBinding) : RecyclerView.ViewHolder(binding.root) {
    private val flexboxLayoutManager: FlexboxLayoutManager by lazy {
        FlexboxLayoutManager(binding.root.context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
    }

    fun bind(typeMatchedResult: TypeMatchedResultUiModel) {
        binding.typeResult = typeMatchedResult

        val textColor = binding.root.context.colorOf(typeMatchedResult.matchedResultColorResId)
        binding.tvResultMyTypeStrength.text =
            SpannableStringBuilder(typeMatchedResult.matchedResult).apply {
                setSpan(
                    ForegroundColorSpan(textColor),
                    0,
                    2,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE,
                )
            }
        binding.ivResultMyType.setImageResource(typeMatchedResult.typeIconResId)
        binding.rvResultMatchedTypes.layoutManager = flexboxLayoutManager
        binding.rvResultMatchedTypes.adapter = TypeResultItemAdapter(typeMatchedResult.matchedItem)
    }
}
