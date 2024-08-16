package poke.rogue.helper.presentation.biome.detail.boss

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBiomePokemonBinding
import poke.rogue.helper.presentation.biome.detail.BiomPokemonAdapter
import poke.rogue.helper.presentation.biome.model.BiomePokemonUiModel
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class BiomeBossViewHolder(private val binding: ItemBiomePokemonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val pokemonAdapter: BiomPokemonAdapter by lazy { BiomPokemonAdapter() }

    fun bind(bossPokemon: BiomePokemonUiModel) {
        binding.biomePokemon = bossPokemon

        val decoration = GridSpacingItemDecoration(3, 18.dp, false)
        binding.rvBiomeWildPokemon.addItemDecoration(decoration)
        bossPokemon.pokemons.let(pokemonAdapter::submitList)
        binding.rvBiomeWildPokemon.adapter = pokemonAdapter
    }
}
