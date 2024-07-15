package poke.rogue.helper.presentation.poketmon2

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding

class PoketmonViewHolder(
    private val binding: ItemPokemonListPokemonBinding,
    private val onClickPokeMonItem: PokeMonItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.listener = onClickPokeMonItem
    }

    fun bind(pokemonUiModel: PokemonUiModel) {
        binding.pokemon = pokemonUiModel
    }
}
