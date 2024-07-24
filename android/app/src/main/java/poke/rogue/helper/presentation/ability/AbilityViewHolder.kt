package poke.rogue.helper.presentation.ability

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemAbilityDescriptionBinding

class AbilityViewHolder(
    private val binding: ItemAbilityDescriptionBinding,
    private val onClickAbilityItem: AbilityUiEventHandler,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(abilityUiModel: AbilityUiModel) {
        binding.ability = abilityUiModel
        binding.uiEventHandler = onClickAbilityItem
    }
}
