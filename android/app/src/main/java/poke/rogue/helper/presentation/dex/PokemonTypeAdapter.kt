package poke.rogue.helper.presentation.dex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import poke.rogue.helper.databinding.ItemTypeRightNameBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class PokemonTypeAdapter :
    ListAdapter<TypeUiModel, PokemonTypeAdapter.PokemonTypeViewHolder>(typeComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PokemonTypeViewHolder =
        PokemonTypeViewHolder(
            ItemTypeRightNameBinding.inflate(
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

    class PokemonTypeViewHolder(private val binding: ItemTypeRightNameBinding) :
        ViewHolder(binding.root) {
        fun bind(type: TypeUiModel) {
            binding.type = type
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
