package poke.rogue.helper.presentation.biome.detail.map

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBiomeMapBinding
import poke.rogue.helper.presentation.biome.BiomeTypesAdapter
import poke.rogue.helper.presentation.biome.model.NextBiomeUiModel
import poke.rogue.helper.presentation.util.view.dp

class BiomeMapViewHolder(private val binding: ItemBiomeMapBinding) :
    RecyclerView.ViewHolder(
        binding.root
    ) {
    fun bind(nextBiome: NextBiomeUiModel) {
        binding.nextBiome = nextBiome

        val typesLayout = binding.flBiomeTypeIcons
        val biomeTypesAdapter =
            BiomeTypesAdapter(
                context = binding.root.context,
                viewGroup = typesLayout,
            )
        biomeTypesAdapter.addTypes(
            types = nextBiome.biome.types,
            spacingBetweenTypes = TYPES_SPACING,
            iconSize = TYPE_ICON_SIZE,
        )
    }

    companion object {
        private val TYPES_SPACING = 5.dp
        private val TYPE_ICON_SIZE = 18.dp
    }
}
