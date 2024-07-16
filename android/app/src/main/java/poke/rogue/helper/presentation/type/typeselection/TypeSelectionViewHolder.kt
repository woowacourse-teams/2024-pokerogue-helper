package poke.rogue.helper.presentation.type.typeselection

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemTypeChoiceBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel

class TypeSelectionViewHolder(
    private val binding: ItemTypeChoiceBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(typeItem: TypeUiModel) {
        binding.ivTypeChoiceIcon.setImageResource(typeItem.typeIconResId)
        binding.tvTypeChoiceName.text = typeItem.name
    }
}
