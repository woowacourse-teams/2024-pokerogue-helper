package poke.rogue.helper.presentation.biome.detail.wild

import android.os.Bundle
import android.view.View
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentBiomeWildPokemonBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.biome.model.BiomeDetailUiModel

class BiomeWildPokemonFragment :
    BindingFragment<FragmentBiomeWildPokemonBinding>(R.layout.fragment_biome_wild_pokemon) {

    private val wildPokemonAdapter: BiomeWildAdapter by lazy { BiomeWildAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        BiomeDetailUiModel.DUMMY.wildPokemons.let(wildPokemonAdapter::submitList)
        binding.rvBiomeWild.adapter = wildPokemonAdapter
    }
}
