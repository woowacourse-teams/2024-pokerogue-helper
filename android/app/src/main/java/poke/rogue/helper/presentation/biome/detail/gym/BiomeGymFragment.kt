package poke.rogue.helper.presentation.biome.detail.gym

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentBiomeGymPokemonBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.biome.detail.BiomeDetailViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted

class BiomeGymFragment :
    ErrorHandleFragment<FragmentBiomeGymPokemonBinding>(R.layout.fragment_biome_gym_pokemon) {
    private val viewModel by activityViewModels<BiomeDetailViewModel>()
    private val gymPokemonAdapter: BiomeGymAdapter by lazy { BiomeGymAdapter(viewModel) }
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
        binding.rvBiomeGym.adapter = gymPokemonAdapter
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect { state ->
                gymPokemonAdapter.submitList(state.gymPokemons)
            }
        }
    }
}
