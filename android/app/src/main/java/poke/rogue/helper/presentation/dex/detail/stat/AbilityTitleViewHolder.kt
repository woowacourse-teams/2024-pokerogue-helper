package poke.rogue.helper.presentation.dex.detail.stat

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemAbilityTitleBinding
import poke.rogue.helper.presentation.dex.detail.PokemonDetailNavigateHandler
import poke.rogue.helper.presentation.dex.model.PokemonDetailAbilityUiModel

class AbilityTitleViewHolder(
    private val binding: ItemAbilityTitleBinding,
    private val onClickAbility: PokemonDetailNavigateHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ability: PokemonDetailAbilityUiModel) {
        binding.ability = ability
        binding.onClickAbility = onClickAbility
    }
}
