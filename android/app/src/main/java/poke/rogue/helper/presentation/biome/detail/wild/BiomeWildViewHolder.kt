package poke.rogue.helper.presentation.biome.detail.wild

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBiomeWildBinding
import poke.rogue.helper.presentation.biome.model.WildPokemonUiModel
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class BiomeWildViewHolder(private val binding: ItemBiomeWildBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val wildPokemonAdapter: BiomeWildItemAdapter by lazy { BiomeWildItemAdapter() }

    fun bind(wildPokemon: WildPokemonUiModel) {
        binding.wildPokemon = wildPokemon

        val decoration = GridSpacingItemDecoration(3, 18.dp, false)
        binding.rvBiomeWildPokemon.addItemDecoration(decoration)
        wildPokemon.pokemons.let(wildPokemonAdapter::submitList)
        binding.rvBiomeWildPokemon.adapter = wildPokemonAdapter
    }
}
