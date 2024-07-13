package poke.rogue.helper.presentation.poketmon

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding

class PoketmonViewHolder(private val binding: ItemPokemonListPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonUiModel: PokemonUiModel) {
        binding.pokemon = pokemonUiModel
    }
}
