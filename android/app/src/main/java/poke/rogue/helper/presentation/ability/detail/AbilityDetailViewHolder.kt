package poke.rogue.helper.presentation.ability.detail

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemAbilityDetailPokemonBinding
import poke.rogue.helper.presentation.dex.PokemonTypesAdapter
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.view.dp

class AbilityDetailViewHolder(
    private val binding: ItemAbilityDetailPokemonBinding,
    private val onClickPokemonItem: AbilityDetailUiEventHandler,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonUiModel: PokemonUiModel) {
        binding.pokemon = pokemonUiModel
        binding.uiEventHandler = onClickPokemonItem

        val typesLayout = binding.layoutAbilityDetailPokemonTypes

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
