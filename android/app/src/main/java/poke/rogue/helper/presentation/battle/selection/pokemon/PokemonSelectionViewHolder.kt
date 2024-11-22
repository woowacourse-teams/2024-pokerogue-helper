package poke.rogue.helper.presentation.battle.selection.pokemon

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBattlePokemonSelectionBinding
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.util.view.dp

class PokemonSelectionViewHolder(
    private val binding: ItemBattlePokemonSelectionBinding,
    private val selectionHandler: PokemonSelectionHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        pokemonSelectionUiModel: PokemonSelectionUiModel,
        isSelected: Boolean,
    ) {
        binding.pokemon = pokemonSelectionUiModel
        binding.isSelected = isSelected
        binding.selectionHandler = selectionHandler

        binding.flexboxTypes.addPokemonTypes(
            types = pokemonSelectionUiModel.types,
            spacingBetweenTypes = 8.dp,
            iconSize = 18.dp,
        )
    }
}
