package poke.rogue.helper.presentation.biome

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBiomeBinding
import poke.rogue.helper.presentation.biome.model.BiomeUiModel
import poke.rogue.helper.presentation.util.view.dp

class BiomeViewHolder(
    private val binding: ItemBiomeBinding,
    private val onClickBiomeItem: BiomeUiEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(biomeUiModel: BiomeUiModel) {
        binding.apply {
            biome = biomeUiModel
            uiEventHandler = onClickBiomeItem
        }

        val typesLayout = binding.flBiomeTypeIcons
        val biomeTypesAdapter =
            BiomeTypesAdapter(
                context = binding.root.context,
                viewGroup = typesLayout,
            )
        biomeTypesAdapter.addTypes(
            types = biomeUiModel.types,
            spacingBetweenTypes = TYPES_SPACING,
            iconSize = TYPE_ICON_SIZE,
        )
    }

    companion object {
        private val TYPES_SPACING = 5.dp
        private val TYPE_ICON_SIZE = 18.dp
    }
}
