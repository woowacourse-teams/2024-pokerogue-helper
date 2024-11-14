package poke.rogue.helper.presentation.dex.detail.stat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemAbilityTitleBinding
import poke.rogue.helper.presentation.dex.detail.PokemonDetailNavigateHandler
import poke.rogue.helper.presentation.dex.model.PokemonDetailAbilityUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class AbilityTitleAdapter(private val onClickAbility: PokemonDetailNavigateHandler) :
    ListAdapter<PokemonDetailAbilityUiModel, AbilityTitleViewHolder>(abilityComparator) {
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
            ItemDiffCallback<PokemonDetailAbilityUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
