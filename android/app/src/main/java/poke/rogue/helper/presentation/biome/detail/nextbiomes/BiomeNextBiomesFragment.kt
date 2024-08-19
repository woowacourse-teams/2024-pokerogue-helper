package poke.rogue.helper.presentation.biome.detail.nextbiomes

import android.os.Bundle
import android.view.View
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentBiomeNextBiomeBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.biome.model.BiomeDetailUiModel

class BiomeNextBiomesFragment :
    BindingFragment<FragmentBiomeNextBiomeBinding>(R.layout.fragment_biome_next_biome) {
    private val nextBiomeAdapter: BiomeNextBiomesAdapter by lazy { BiomeNextBiomesAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        BiomeDetailUiModel.DUMMY.nextBiomes.let(nextBiomeAdapter::submitList)
        binding.rvBiomeNextBiome.adapter = nextBiomeAdapter
    }
}
