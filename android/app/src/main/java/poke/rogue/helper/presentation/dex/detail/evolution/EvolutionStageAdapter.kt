package poke.rogue.helper.presentation.dex.detail.evolution

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.presentation.dex.detail.PokemonDetailNavigateHandler
import poke.rogue.helper.presentation.dex.model.EvolutionsUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback
import timber.log.Timber

class EvolutionStageAdapter(
    private val navigateHandler: PokemonDetailNavigateHandler,
) : ListAdapter<EvolutionsUiModel, EvolutionStageViewHolder>(comparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): EvolutionStageViewHolder = EvolutionStageViewHolder.inflated(parent, navigateHandler)

    override fun onBindViewHolder(
        holder: EvolutionStageViewHolder,
        position: Int,
    ) {
        Timber.d("holder: $holder")
        holder.bind(getItem(position))
    }

    companion object {
        val comparator =
            ItemDiffCallback<EvolutionsUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.evolutions == newItem.evolutions },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
