package poke.rogue.helper.presentation.biome.detail.wild


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

// todo uiHandler
class BiomeWildItemAdapter() :
    ListAdapter<PokemonUiModel, BiomeWildItemViewHolder>(poketmonComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BiomeWildItemViewHolder =
        BiomeWildItemViewHolder(
            ItemPokemonListPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        holder: BiomeWildItemViewHolder,
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


