package poke.rogue.helper.presentation.poketmon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class PokemonAdapter : ListAdapter<PokemonUiModel, PoketmonViewHolder>(poketmonComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PoketmonViewHolder =
        PoketmonViewHolder(
            ItemPokemonListPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        viewHolder: PoketmonViewHolder,
        position: Int,
    ) {
        viewHolder.bind(getItem(position))
    }

    companion object {
        val poketmonComparator =
            ItemDiffCallback<PokemonUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
