package poke.rogue.helper.presentation.biome.detail.nextbiomes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemBiomeNextBiomesBinding
import poke.rogue.helper.presentation.biome.detail.BiomeDetailHandler
import poke.rogue.helper.presentation.biome.model.NextBiomeUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class BiomeNextBiomesAdapter(private val onClickNextBiome: BiomeDetailHandler) :
    ListAdapter<NextBiomeUiModel, BiomeNextBiomesViewHolder>(biomeComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BiomeNextBiomesViewHolder {
        return BiomeNextBiomesViewHolder(
            ItemBiomeNextBiomesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClickNextBiome,
        )
    }

    override fun onBindViewHolder(
        holder: BiomeNextBiomesViewHolder,
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
