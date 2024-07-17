package poke.rogue.helper.presentation.dex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import poke.rogue.helper.databinding.ItemStatBinding
import poke.rogue.helper.presentation.dex.detail.Stat
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class PokemonStatAdapter() :
    ListAdapter<Stat, PokemonStatAdapter.PokemonStatViewHolder>(statComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PokemonStatViewHolder =
        PokemonStatViewHolder(
            ItemStatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        viewHolder: PokemonStatViewHolder,
        position: Int,
    ) {
        viewHolder.bind(getItem(position))
    }

    class PokemonStatViewHolder(private val binding: ItemStatBinding) :
        ViewHolder(binding.root) {
        fun bind(stat: Stat) {
            binding.stat = stat
        }
    }

    companion object {
        private val statComparator =
            ItemDiffCallback<Stat>(
                onItemsTheSame = { oldItem, newItem -> oldItem.name == newItem.name },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
