package poke.rogue.helper.presentation.dex.detail.evolution

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.presentation.dex.detail.PokemonDetailNavigateHandler
import poke.rogue.helper.presentation.dex.model.EvolutionsUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback
import timber.log.Timber

class OuterEvolutionAdapter(
    private val navigateHandler: PokemonDetailNavigateHandler,
) : ListAdapter<EvolutionsUiModel, OuterEvolutionViewHolder>(comparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OuterEvolutionViewHolder = OuterEvolutionViewHolder.inflated(parent, navigateHandler)

    override fun onBindViewHolder(
        holder: OuterEvolutionViewHolder,
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
