package poke.rogue.helper.presentation.dex

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.view.dp

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
            config = typesUiConfig,
        )
    }

    companion object {
        private val typesUiConfig =
            TypeChip.PokemonTypeViewConfiguration(
                hasBackGround = true,
                nameSize = 8.dp,
                iconSize = 18.dp,
                spacing = 0.dp,
                marginBetweenTypes = 0f,
            )
    }
}
