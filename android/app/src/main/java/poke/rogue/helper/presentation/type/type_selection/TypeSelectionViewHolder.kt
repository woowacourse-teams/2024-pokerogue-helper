package poke.rogue.helper.presentation.type.type_selection

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemTypeChoiceBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel

class TypeSelectionViewHolder(
    private val binding: ItemTypeChoiceBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(typeItem: TypeUiModel) {
        binding.ivTypeChoiceIcon.setImageResource(typeItem.iconResId)
        binding.tvTypeChoiceName.text = typeItem.name
    }
}
