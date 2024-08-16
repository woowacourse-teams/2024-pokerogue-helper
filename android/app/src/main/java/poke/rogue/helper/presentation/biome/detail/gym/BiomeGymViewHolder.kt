package poke.rogue.helper.presentation.biome.detail.gym

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBiomeGymBinding
import poke.rogue.helper.presentation.biome.detail.wild.BiomeWildItemAdapter
import poke.rogue.helper.presentation.biome.model.BiomePokemonUiModel
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class BiomeGymViewHolder(private val binding: ItemBiomeGymBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val pokemonAdapter: BiomeWildItemAdapter by lazy { BiomeWildItemAdapter() }

    fun bind(gymPokemon: BiomePokemonUiModel) {
        binding.gymLeader = gymPokemon

        val decoration = GridSpacingItemDecoration(3, 18.dp, false)
        binding.rvBiomeGymPokemon.addItemDecoration(decoration)
        gymPokemon.pokemons.let(pokemonAdapter::submitList)
        binding.rvBiomeGymPokemon.adapter = pokemonAdapter
    }
}
