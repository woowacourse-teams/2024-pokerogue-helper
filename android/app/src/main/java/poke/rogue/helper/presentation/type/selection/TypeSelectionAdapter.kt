package poke.rogue.helper.presentation.type.selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemTypeSelectionBinding
import poke.rogue.helper.presentation.type.TypeHandler
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel

class TypeSelectionAdapter(
    private val types: List<TypeUiModel> = listOf(),
    private val selector: SelectorType,
    private val disabledType: Set<TypeUiModel> = setOf(),
    private val typeHandler: TypeHandler,
) :
    RecyclerView.Adapter<TypeSelectionViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TypeSelectionViewHolder {
        val view = ItemTypeSelectionBinding.inflate(LayoutInflater.from(parent.context))
        return TypeSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return types.size
    }

    override fun onBindViewHolder(
        holder: TypeSelectionViewHolder,
        position: Int,
    ) {
        val item = types[position]
        holder.bind(item, selector, disabledType, typeHandler)
    }
}
