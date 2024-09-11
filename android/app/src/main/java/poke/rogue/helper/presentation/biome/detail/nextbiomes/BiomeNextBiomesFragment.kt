package poke.rogue.helper.presentation.biome.detail.nextbiomes

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentBiomeNextBiomeBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.biome.detail.BiomeDetailViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted

class BiomeNextBiomesFragment :
    ErrorHandleFragment<FragmentBiomeNextBiomeBinding>(R.layout.fragment_biome_next_biome) {
    private val viewModel by activityViewModels<BiomeDetailViewModel>()
    private val nextBiomeAdapter: BiomeNextBiomesAdapter by lazy { BiomeNextBiomesAdapter(viewModel) }
    override val errorViewModel: ErrorHandleViewModel
        get() = viewModel
    override val toolbar: Toolbar? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        binding.rvBiomeNextBiome.adapter = nextBiomeAdapter
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect { state ->
                nextBiomeAdapter.submitList(state.nextBiomes)
            }
        }
    }
}
