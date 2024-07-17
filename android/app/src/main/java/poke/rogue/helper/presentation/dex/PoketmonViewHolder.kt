package poke.rogue.helper.presentation.dex

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel

class PoketmonViewHolder(
    private val binding: ItemPokemonListPokemonBinding,
    private val onClickPokeMonItem: PokeMonItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.listener = onClickPokeMonItem
    }

    fun bind(pokemonUiModel: PokemonUiModel) {
        binding.pokemon = pokemonUiModel
        binding.rvPokeTypeList.apply {
            val types = pokemonUiModel.types.mapNotNull(TypeUiModel::fromType)
            adapter =
                PokemonTypeAdapter().apply {
                    submitList(types)
                }
        }
    }
}
