package poke.rogue.helper.presentation.dex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import poke.rogue.helper.databinding.ItemTypeBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class PokemonTypeAdapter :
    ListAdapter<TypeUiModel, PokemonTypeAdapter.PokemonTypeViewHolder>(typeComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PokemonTypeViewHolder =
        PokemonTypeViewHolder(
            ItemTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        viewHolder: PokemonTypeViewHolder,
        position: Int,
    ) {
        viewHolder.bind(getItem(position))
    }

    class PokemonTypeViewHolder(private val binding: ItemTypeBinding) :
        ViewHolder(binding.root) {
        fun bind(type: TypeUiModel) {
            binding.type = type
            binding.isEmptyBackground = false
        }
    }

    companion object {
        private val typeComparator =
            ItemDiffCallback<TypeUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
