package poke.rogue.helper.presentation.type.view

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import poke.rogue.helper.databinding.ItemTypeResultBinding
import poke.rogue.helper.presentation.type.TypeResultItemAdapter
import poke.rogue.helper.presentation.type.model.TypeMatchedResultUiModel

class TypeResultView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {
    private val binding: ItemTypeResultBinding by lazy {
        ItemTypeResultBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private val flexboxLayoutManager: FlexboxLayoutManager by lazy {
        FlexboxLayoutManager(context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
    }

    fun bind(typeResult: TypeMatchedResultUiModel) {
        binding.typeResult = typeResult
        binding.tvResultMyTypeStrength.text =
            SpannableStringBuilder(typeResult.matchedResult).apply {
                setSpan(
                    ForegroundColorSpan(Color.RED),
                    0,
                    2,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE,
                )
            }
        binding.ivResultMyType.setImageResource(typeResult.typeIconResId)
        binding.rvResultMatchedTypes.layoutManager = flexboxLayoutManager
        binding.rvResultMatchedTypes.adapter = TypeResultItemAdapter(typeResult.matchedItem)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("typeResult")
        fun setTypeResult(
            view: TypeResultView,
            typeResult: TypeMatchedResultUiModel,
        ) {
            view.bind(typeResult)
        }
    }
}
