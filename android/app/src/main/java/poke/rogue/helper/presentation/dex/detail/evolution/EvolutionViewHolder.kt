package poke.rogue.helper.presentation.dex.detail.evolution

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonDetailEvolutionBinding
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel

class EvolutionViewHolder(
    private val binding: ItemPokemonDetailEvolutionBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonEvolutionUiModel: SingleEvolutionUiModel) {
        binding.evolution = pokemonEvolutionUiModel
    }
}
