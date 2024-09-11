package poke.rogue.helper.presentation.battle.selection.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemBattlePokemonSelectionBinding
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.dex.filter.SelectableUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class PokemonSelectionAdapter(
    private val selectionHandler: PokemonSelectionHandler,
) : ListAdapter<SelectableUiModel<PokemonSelectionUiModel>, PokemonSelectionViewHolder>(pokemonComparator) {
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
        viewHolder.bind(pokemon.data, pokemon.isSelected)
    }

    companion object {
        private val pokemonComparator =
            ItemDiffCallback<SelectableUiModel<PokemonSelectionUiModel>>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
