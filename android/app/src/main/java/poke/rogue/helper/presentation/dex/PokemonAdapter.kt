package poke.rogue.helper.presentation.dex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class PokemonAdapter(private val onClickPokeMonItem: PokemonListNavigateHandler) :
    ListAdapter<PokemonUiModel, PokemonViewHolder>(poketmonComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PokemonViewHolder =
        PokemonViewHolder(
            ItemPokemonListPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClickPokeMonItem,
        )

    override fun onBindViewHolder(
        viewHolder: PokemonViewHolder,
        position: Int,
    ) {
        viewHolder.bind(getItem(position))
    }

    companion object {
        val poketmonComparator =
            ItemDiffCallback<PokemonUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
