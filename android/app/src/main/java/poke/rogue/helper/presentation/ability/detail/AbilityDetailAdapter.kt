package poke.rogue.helper.presentation.ability.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemAbilityDetailPokemonBinding
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class AbilityDetailAdapter(private val onClickPokemonItem: AbilityDetailUiEventHandler) :
    ListAdapter<PokemonUiModel, AbilityDetailViewHolder>(poketmonComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AbilityDetailViewHolder =
        AbilityDetailViewHolder(
            ItemAbilityDetailPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClickPokemonItem,
        )

    override fun onBindViewHolder(
        holder: AbilityDetailViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        val poketmonComparator =
            ItemDiffCallback<PokemonUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
