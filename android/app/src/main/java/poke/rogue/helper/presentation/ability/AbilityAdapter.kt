package poke.rogue.helper.presentation.ability

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemAbilityDescriptionBinding
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class AbilityAdapter : ListAdapter<AbilityUiModel, AbilityViewHolder>(abilityComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder {
        return AbilityViewHolder(
            ItemAbilityDescriptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val abilityComparator = ItemDiffCallback<AbilityUiModel>(
            onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
        )
    }
}
