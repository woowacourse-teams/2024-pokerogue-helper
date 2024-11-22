package poke.rogue.helper.presentation.biome.detail.wild

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBiomePokemonBinding
import poke.rogue.helper.presentation.biome.detail.BiomPokemonAdapter
import poke.rogue.helper.presentation.biome.model.BiomePokemonUiModel
import poke.rogue.helper.presentation.dex.PokemonListNavigateHandler
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class BiomeWildViewHolder(
    private val binding: ItemBiomePokemonBinding,
    private val onClickPokemon: PokemonListNavigateHandler,
) :
    RecyclerView.ViewHolder(binding.root) {
    private val pokemonAdapter: BiomPokemonAdapter by lazy { BiomPokemonAdapter(onClickPokemon) }

    init {
        val decoration = GridSpacingItemDecoration(3, 18.dp, false)
        binding.rvBiomeWildPokemon.addItemDecoration(decoration)
    }

    fun bind(wildPokemon: BiomePokemonUiModel) {
        binding.biomePokemon = wildPokemon

        wildPokemon.pokemons.let(pokemonAdapter::submitList)
        binding.rvBiomeWildPokemon.adapter = pokemonAdapter
    }
}
