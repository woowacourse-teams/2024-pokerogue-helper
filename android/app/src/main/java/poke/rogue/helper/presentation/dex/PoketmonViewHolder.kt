package poke.rogue.helper.presentation.dex

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.dex.detail.PokemonTypesAdapter
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.view.PokemonTypeViewConfiguration

class PoketmonViewHolder(
    private val binding: ItemPokemonListPokemonBinding,
    onClickPokeMonItem: PokemonListNavigateHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.listener = onClickPokeMonItem
    }

    fun bind(pokemonUiModel: PokemonUiModel) {
        binding.pokemon = pokemonUiModel
        val typesLayout = binding.layoutItemPokemonPokemonTypes

        val pokemonTypesAdapter =
            PokemonTypesAdapter(
                context = binding.root.context,
                viewGroup = typesLayout,
            )

        pokemonTypesAdapter.addTypes(
            types = pokemonUiModel.types,
            config = TypesUiConfig,
        )
    }

    companion object {
        private val TypesUiConfig =
            PokemonTypeViewConfiguration(
                isEmptyBackground = false,
                nameSize = 10f,
                iconSize = 20f,
                marginBetweenTypes = 0,
            )
    }
}
