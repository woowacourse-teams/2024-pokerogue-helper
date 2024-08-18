package poke.rogue.helper.presentation.dex.detail.information

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.databinding.ItemPokemonDetailInformationBiomeBinding
import poke.rogue.helper.presentation.dex.detail.PokemonDetailNavigateHandler

class PokemonDetailBiomeViewHolder(
    private val binding: ItemPokemonDetailInformationBiomeBinding,
    private val onClickBiomeItem: PokemonDetailNavigateHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(biome: Biome) {
        binding.apply {
            this.biome = biome
            uiEventHandler = onClickBiomeItem
        }
    }
}