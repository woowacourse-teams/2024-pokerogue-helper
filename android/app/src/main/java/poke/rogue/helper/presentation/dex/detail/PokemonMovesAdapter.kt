package poke.rogue.helper.presentation.dex.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemPokemonDetailMovesBinding
import poke.rogue.helper.presentation.dex.model.PokemonMoveUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class PokemonMovesAdapter : ListAdapter<PokemonMoveUiModel, PokemonMoveViewHolder>(moveComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PokemonMoveViewHolder =
        PokemonMoveViewHolder(
            ItemPokemonDetailMovesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        holder: PokemonMoveViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        private val moveComparator =
            ItemDiffCallback<PokemonMoveUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
