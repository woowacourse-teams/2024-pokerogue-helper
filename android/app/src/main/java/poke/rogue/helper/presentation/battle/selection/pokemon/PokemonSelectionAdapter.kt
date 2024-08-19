package poke.rogue.helper.presentation.battle.selection.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemBattlePokemonSelectionBinding
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionHandler
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class PokemonSelectionAdapter(
    private val selectionHandler: BattleSelectionHandler,
) : ListAdapter<PokemonSelectionUiModel, PokemonSelectionViewHolder>(pokemonComparator) {
    private var selectedPokemonId: String? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PokemonSelectionViewHolder =
        PokemonSelectionViewHolder(
            ItemBattlePokemonSelectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            selectionHandler,
        )

    override fun onBindViewHolder(
        viewHolder: PokemonSelectionViewHolder,
        position: Int,
    ) {
        val pokemon = getItem(position)
        val isSelected = pokemon.id == selectedPokemonId
        viewHolder.bind(pokemon, isSelected)
    }

    fun updateSelectedPokemon(selectedId: String) {
        var previousSelectedPosition: Int? = null
        var newSelectedPosition: Int? = null

        currentList.forEachIndexed { index, pokemon ->
            if (pokemon.id == selectedPokemonId) {
                previousSelectedPosition = index
            }
            if (pokemon.id == selectedId) {
                newSelectedPosition = index
            }
            if (previousSelectedPosition != null && newSelectedPosition != null) {
                return@forEachIndexed
            }
        }

        selectedPokemonId = selectedId
        previousSelectedPosition?.let { notifyItemChanged(it) }
        newSelectedPosition?.let { notifyItemChanged(it) }
    }

    companion object {
        private val pokemonComparator =
            ItemDiffCallback<PokemonSelectionUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
