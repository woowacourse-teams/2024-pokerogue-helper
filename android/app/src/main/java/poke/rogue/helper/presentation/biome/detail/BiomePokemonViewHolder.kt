package poke.rogue.helper.presentation.biome.detail

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.dex.PokemonTypesAdapter
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.view.dp

class BiomePokemonViewHolder(
    private val binding: ItemPokemonListPokemonBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonItem: PokemonUiModel) {
        binding.pokemon = pokemonItem

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
                nameSize = 8.dp,
                iconSize = 18.dp,
                spacing = 0.dp,
            )
    }
}
