
package poke.rogue.helper.presentation.dex.detail.evolution

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemPokemonDetailEvolutionBinding
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class EvolutionAdapter : ListAdapter<SingleEvolutionUiModel, EvolutionViewHolder>(evolutionComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): EvolutionViewHolder =
        EvolutionViewHolder(
            ItemPokemonDetailEvolutionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        holder: EvolutionViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        private val evolutionComparator =
            ItemDiffCallback<SingleEvolutionUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.pokemonId == newItem.pokemonId },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
