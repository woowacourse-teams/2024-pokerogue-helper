package poke.rogue.helper.presentation.dex

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.dex.model.PokemonUiModel

class PoketmonViewHolder(
    private val binding: ItemPokemonListPokemonBinding,
    private val onClickPokeMonItem: PokemonDetailNavigateHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.listener = onClickPokeMonItem
    }

    fun bind(pokemonUiModel: PokemonUiModel) {
        binding.pokemon = pokemonUiModel
        binding.rvPokeTypeList.adapter =
            PokemonTypeAdapter().apply {
                submitList(pokemonUiModel.types)
            }
    }
}
