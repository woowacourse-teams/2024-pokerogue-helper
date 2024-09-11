package poke.rogue.helper.presentation.biome.detail.gym

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBiomeGymBinding
import poke.rogue.helper.presentation.biome.detail.BiomPokemonAdapter
import poke.rogue.helper.presentation.biome.model.BiomePokemonUiModel
import poke.rogue.helper.presentation.dex.PokemonListNavigateHandler
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class BiomeGymViewHolder(
    private val binding: ItemBiomeGymBinding,
    private val onClickPokemon: PokemonListNavigateHandler,
) :
    RecyclerView.ViewHolder(
            binding.root,
        ) {
    private val pokemonAdapter: BiomPokemonAdapter by lazy { BiomPokemonAdapter(onClickPokemon) }

    fun bind(gymPokemon: BiomePokemonUiModel) {
        binding.gymLeader = gymPokemon

        val decoration = GridSpacingItemDecoration(3, 9.dp, false)
        binding.rvBiomeGymPokemon.addItemDecoration(decoration)
        gymPokemon.pokemons.let(pokemonAdapter::submitList)
        binding.rvBiomeGymPokemon.adapter = pokemonAdapter
    }
}
