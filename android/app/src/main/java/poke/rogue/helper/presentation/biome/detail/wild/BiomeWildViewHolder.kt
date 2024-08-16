package poke.rogue.helper.presentation.biome.detail.wild

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBiomePokemonBinding
import poke.rogue.helper.presentation.biome.detail.BiomPokemonAdapter
import poke.rogue.helper.presentation.biome.model.BiomePokemonUiModel
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class BiomeWildViewHolder(private val binding: ItemBiomePokemonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val pokemonAdapter: BiomPokemonAdapter by lazy { BiomPokemonAdapter() }

    fun bind(wildPokemon: BiomePokemonUiModel) {
        binding.biomePokemon = wildPokemon

        val decoration = GridSpacingItemDecoration(3, 18.dp, false)
        binding.rvBiomeWildPokemon.addItemDecoration(decoration)
        wildPokemon.pokemons.let(pokemonAdapter::submitList)
        binding.rvBiomeWildPokemon.adapter = pokemonAdapter
    }
}
