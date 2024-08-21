package poke.rogue.helper.presentation.dex

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.ui.component.PokeChip
import poke.rogue.helper.ui.layout.PaddingValues

class PokemonViewHolder(
    private val binding: ItemPokemonListPokemonBinding,
    onClickPokeMonItem: PokemonListNavigateHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.listener = onClickPokeMonItem
    }

    fun bind(pokemonUiModel: PokemonUiModel) {
        binding.pokemon = pokemonUiModel
        binding.spec =
            PokeChip.Spec(
                label = pokemonUiModel.displayStat.toString(),
                sizes =
                    PokeChip.Sizes(
                        labelSize = 10,
                    ),
                colors =
                    PokeChip.Colors(
                        labelColor = R.color.poke_grey_80,
                        selectedContainerColor = R.color.lemon,
                    ),
                strokeWidth = 0.dp,
                padding =
                    PaddingValues(
                        start = 4.dp,
                        top = 2.dp,
                        end = 4.dp,
                        bottom = 2.dp,
                    ),
                isSelected = true,
            )
        val typesLayout = binding.layoutItemPokemonPokemonTypes

        val pokemonTypesAdapter =
            PokemonTypesAdapter(
                context = binding.root.context,
                viewGroup = typesLayout,
            )

        pokemonTypesAdapter.addTypes(
            types = pokemonUiModel.types,
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
