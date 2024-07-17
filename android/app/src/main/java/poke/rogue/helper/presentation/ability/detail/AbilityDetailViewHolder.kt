package poke.rogue.helper.presentation.ability.detail

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemAbilityDetailPokemonBinding
import poke.rogue.helper.presentation.dex.PokemonUiModel

class AbilityDetailViewHolder(private val binding: ItemAbilityDetailPokemonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonUiModel: PokemonUiModel) {
        binding.pokemon = pokemonUiModel
    }
}
