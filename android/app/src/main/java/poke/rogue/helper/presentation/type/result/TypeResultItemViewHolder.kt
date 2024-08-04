package poke.rogue.helper.presentation.type.result

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemTypeNameBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel

class TypeResultItemViewHolder(
    private val binding: ItemTypeNameBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(typeItem: TypeUiModel) {
        binding.typeItem = typeItem
    }
}