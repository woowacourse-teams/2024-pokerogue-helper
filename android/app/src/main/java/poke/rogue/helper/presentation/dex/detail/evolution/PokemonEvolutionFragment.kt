package poke.rogue.helper.presentation.dex.detail.evolution

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonEvolutionBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.detail.PokemonDetailUiState
import poke.rogue.helper.presentation.dex.detail.PokemonDetailViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted

class PokemonEvolutionFragment : BindingFragment<FragmentPokemonEvolutionBinding>(R.layout.fragment_pokemon_evolution) {
    private val activityViewModel: PokemonDetailViewModel by activityViewModel()

    private val evolutionStageAdapter by lazy {
        EvolutionStageAdapter(activityViewModel)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initObserver()
    }

    private fun initAdapter() {
        binding.apply {
            rvPokemonDetailEvolutions.adapter = evolutionStageAdapter
        }
    }

    private fun initObserver() {
        repeatOnStarted {
            activityViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is PokemonDetailUiState.IsLoading -> {}
                    is PokemonDetailUiState.Success -> {
                        binding.evolutions = uiState.evolutions

                        uiState.evolutions.apply {
                            evolutionStageAdapter.submitList(
                                this.evolutions(),
                            )
                        }
                    }
                }
            }
        }
    }
}
