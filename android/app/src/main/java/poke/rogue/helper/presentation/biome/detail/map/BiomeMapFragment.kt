package poke.rogue.helper.presentation.biome.detail.map

import android.os.Bundle
import android.view.View
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentBiomeNextBiomeBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.biome.model.BiomeDetailUiModel

class BiomeMapFragment :
    BindingFragment<FragmentBiomeNextBiomeBinding>(R.layout.fragment_biome_next_biome) {
    private val nextBiomeAdapter: BiomeMapAdapter by lazy { BiomeMapAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        BiomeDetailUiModel.DUMMY.map.let(nextBiomeAdapter::submitList)
        binding.rvBiomeNextBiome.adapter = nextBiomeAdapter
    }
}
