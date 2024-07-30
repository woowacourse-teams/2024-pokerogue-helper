package poke.rogue.helper.presentation.dex.detail

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemAbilityTitleBinding
import poke.rogue.helper.presentation.dex.model.AbilityTitleUiModel

class AbilityTitleViewHolder(
    private val binding: ItemAbilityTitleBinding,
    private val onClickAbility: PokemonDetailNavigateHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ability: AbilityTitleUiModel) {
        binding.ability = ability
        binding.onClickAbility = onClickAbility
    }
}
