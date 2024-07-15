package poke.rogue.helper.presentation.type

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemTypeNameBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel

class TypeResultViewHolder(
    private val binding: ItemTypeNameBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(typeItem: TypeUiModel) {
        binding.typeItem = typeItem
    }
}
