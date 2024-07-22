package poke.rogue.helper.presentation.type.selection

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemTypeSelectionBinding
import poke.rogue.helper.presentation.type.TypeHandler
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel

class TypeSelectionViewHolder(
    private val binding: ItemTypeSelectionBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        selector: SelectorType,
        typeItem: TypeUiModel,
        typeHandler: TypeHandler,
    ) {
        binding.typeItem = typeItem
        binding.selector = selector
        binding.typeHandler = typeHandler
    }
}
