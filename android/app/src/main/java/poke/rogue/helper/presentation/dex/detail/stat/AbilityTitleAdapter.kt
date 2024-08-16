package poke.rogue.helper.presentation.dex.detail.stat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemAbilityTitleBinding
import poke.rogue.helper.presentation.dex.detail.PokemonDetailNavigateHandler
import poke.rogue.helper.presentation.dex.model.AbilityTitleUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class AbilityTitleAdapter(private val onClickAbility: PokemonDetailNavigateHandler) :
    ListAdapter<AbilityTitleUiModel, AbilityTitleViewHolder>(abilityComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AbilityTitleViewHolder =
        AbilityTitleViewHolder(
            ItemAbilityTitleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClickAbility,
        )

    override fun onBindViewHolder(
        viewHolder: AbilityTitleViewHolder,
        position: Int,
    ) {
        viewHolder.bind(getItem(position))
    }

    companion object {
        private val abilityComparator =
            ItemDiffCallback<AbilityTitleUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
