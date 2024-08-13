package poke.rogue.helper.presentation.biome.detail.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemBiomeMapBinding
import poke.rogue.helper.presentation.biome.model.NextBiomeUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class BiomeMapAdapter :
    ListAdapter<NextBiomeUiModel, BiomeMapViewHolder>(biomeComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BiomeMapViewHolder {
        return BiomeMapViewHolder(
            ItemBiomeMapBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(
        holder: BiomeMapViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        val biomeComparator =
            ItemDiffCallback<NextBiomeUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.biome.id == newItem.biome.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
