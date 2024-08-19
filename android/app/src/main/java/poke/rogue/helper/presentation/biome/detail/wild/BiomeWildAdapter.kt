package poke.rogue.helper.presentation.biome.detail.wild

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemBiomePokemonBinding
import poke.rogue.helper.presentation.biome.model.BiomePokemonUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class BiomeWildAdapter :
    ListAdapter<BiomePokemonUiModel, BiomeWildViewHolder>(wildPokemonComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BiomeWildViewHolder {
        return BiomeWildViewHolder(
            ItemBiomePokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(
        holder: BiomeWildViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        val wildPokemonComparator =
            ItemDiffCallback<BiomePokemonUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem == newItem },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
