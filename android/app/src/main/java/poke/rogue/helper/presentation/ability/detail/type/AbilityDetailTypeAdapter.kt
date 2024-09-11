package poke.rogue.helper.presentation.ability.detail.type

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemTypeRightNameBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class AbilityDetailTypeAdapter :
    ListAdapter<TypeUiModel, AbilityDetailTypeAdapter.PokemonTypeViewHolder>(typeComparator) {
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
        RecyclerView.ViewHolder(binding.root) {
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
