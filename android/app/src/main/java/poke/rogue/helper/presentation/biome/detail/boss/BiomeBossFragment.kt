package poke.rogue.helper.presentation.biome.detail.boss

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentBiomeBossPokemonBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.biome.detail.BiomeDetailViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted

class BiomeBossFragment :
    ErrorHandleFragment<FragmentBiomeBossPokemonBinding>(R.layout.fragment_biome_boss_pokemon) {
    private val viewModel by activityViewModels<BiomeDetailViewModel>()
    private val bossPokemonAdapter: BiomeBossAdapter by lazy { BiomeBossAdapter(viewModel) }
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
        binding.rvBiomeBoss.adapter = bossPokemonAdapter
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect { state ->
                state.bossPokemons.isEmpty().let {
                    binding.biomeDetailEmpty.visibility = if (it) View.VISIBLE else View.GONE
                }
                bossPokemonAdapter.submitList(state.bossPokemons)
            }
        }
    }
}
