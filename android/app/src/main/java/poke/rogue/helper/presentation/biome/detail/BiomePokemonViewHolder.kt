package poke.rogue.helper.presentation.biome.detail

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.dex.PokemonListNavigateHandler
import poke.rogue.helper.presentation.dex.PokemonTypesAdapter
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.view.dp

class BiomePokemonViewHolder(
    private val binding: ItemPokemonListPokemonBinding,
    private val onClickPokemon: PokemonListNavigateHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonItem: PokemonUiModel) {
        binding.pokemon = pokemonItem
        binding.listener = onClickPokemon
        val typesLayout = binding.layoutItemPokemonPokemonTypes

        val pokemonTypesAdapter =
            PokemonTypesAdapter(
                context = binding.root.context,
                viewGroup = typesLayout,
            )

        pokemonTypesAdapter.addTypes(
            types = pokemonItem.types,
            config = typesUiConfig,
            spacingBetweenTypes = 0.dp,
        )
    }

    companion object {
        private val typesUiConfig =
            TypeChip.PokemonTypeViewConfiguration(
                hasBackGround = true,
                nameSize = 7.dp,
                iconSize = 14.dp,
                spacing = 0.dp,
            )
    }
}
