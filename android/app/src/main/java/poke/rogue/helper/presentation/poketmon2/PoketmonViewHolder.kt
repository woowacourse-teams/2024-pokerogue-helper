package poke.rogue.helper.presentation.poketmon2

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PoketmonViewHolder(
    private val binding: ItemPokemonListPokemonBinding,
    private val onClickPokeMonItem: PokeMonItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.listener = onClickPokeMonItem
    }

    fun bind(pokemonUiModel: PokemonUiModel) {
        binding.pokemon = pokemonUiModel
        binding.rvPokeTypeList.apply {
            val types = pokemonUiModel.types.mapNotNull { TypeUiModel.fromName(it) }
            adapter = PokemonTypeAdapter().apply {
                submitList(types)
            }
        }
    }
}
