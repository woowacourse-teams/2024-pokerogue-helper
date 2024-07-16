package poke.rogue.helper.presentation.type.typeselection

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemTypeChoiceBinding
import poke.rogue.helper.presentation.type.TypeHandler
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel

class TypeSelectionViewHolder(
    private val binding: ItemTypeChoiceBinding,
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
