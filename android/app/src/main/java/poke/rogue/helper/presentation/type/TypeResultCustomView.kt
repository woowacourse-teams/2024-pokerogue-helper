package poke.rogue.helper.presentation.type

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import poke.rogue.helper.databinding.ItemTypeResultBinding
import poke.rogue.helper.presentation.type.model.TypeMatchedResultUiModel

class TypeResultCustomView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {
    private val binding: ItemTypeResultBinding
    private val flexboxLayoutManager: FlexboxLayoutManager

    init {
        binding = ItemTypeResultBinding.inflate(LayoutInflater.from(context), this, true)
        flexboxLayoutManager =
            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
            }
    }

    fun bind(typeResult: TypeMatchedResultUiModel) {
        binding.typeResult = typeResult
        binding.ivResultMyType.setImageResource(typeResult.typeIconResId)

        binding.rvResultMatchedTypes.layoutManager = flexboxLayoutManager
        binding.rvResultMatchedTypes.adapter = TypeResultAdapter(typeResult.matchedItem)
    }
}
