package poke.rogue.helper.presentation.battle.selection.pokemon

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBattlePokemonSelectionBinding
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.biome.BiomeTypesAdapter
import poke.rogue.helper.presentation.util.view.dp

class PokemonSelectionViewHolder(private val binding: ItemBattlePokemonSelectionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonSelectionUiModel: PokemonSelectionUiModel) {
        binding.pokemon = pokemonSelectionUiModel

        val typeAdapter = BiomeTypesAdapter(context = binding.root.context, binding.flexboxTypes)
        typeAdapter.addTypes(
            types = pokemonSelectionUiModel.types,
            spacingBetweenTypes = 8.dp,
            iconSize = 18.dp,
        )
    }
}
