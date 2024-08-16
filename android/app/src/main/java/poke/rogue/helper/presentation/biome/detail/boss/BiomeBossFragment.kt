package poke.rogue.helper.presentation.biome.detail.boss

import android.os.Bundle
import android.view.View
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentBiomeBossPokemonBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.biome.model.BiomeDetailUiModel

class BiomeBossFragment :
    BindingFragment<FragmentBiomeBossPokemonBinding>(R.layout.fragment_biome_boss_pokemon) {

    private val bossPokemonAdapter: BiomeBossAdapter by lazy { BiomeBossAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        BiomeDetailUiModel.DUMMY.bossPokemons.let(bossPokemonAdapter::submitList)
        binding.rvBiomeBoss.adapter = bossPokemonAdapter
    }
}
