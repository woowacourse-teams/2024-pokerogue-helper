package poke.rogue.helper.presentation.dex.sort

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonSortBinding
import poke.rogue.helper.presentation.dex.filter.SelectableUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class PokeSortAdapter(
    private val onToggleSort: PokemonSortHandler,
) : ListAdapter<SelectableUiModel<PokemonSortUiModel>, PokeSortAdapter.PokeSortViewHolder>(
        sortComparator,
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PokeSortViewHolder {
        val binding =
            ItemPokemonSortBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeSortViewHolder(binding, onToggleSort)
    }

    override fun onBindViewHolder(
        holder: PokeSortViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    class PokeSortViewHolder(
        private val binding: ItemPokemonSortBinding,
        private val onToggleSort: PokemonSortHandler,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(selectableSort: SelectableUiModel<PokemonSortUiModel>) {
            binding.sort = selectableSort
            binding.handler = onToggleSort
        }
    }

    companion object {
        private val sortComparator =
            ItemDiffCallback<SelectableUiModel<PokemonSortUiModel>>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
