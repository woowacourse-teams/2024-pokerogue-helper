package poke.rogue.helper.presentation.dex.detail.evolution

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonEvolutionBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.detail.PokemonDetailUiState
import poke.rogue.helper.presentation.dex.detail.PokemonDetailViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted
import timber.log.Timber

class PokemonEvolutionFragment : BindingFragment<FragmentPokemonEvolutionBinding>(R.layout.fragment_pokemon_evolution) {
    private val activityViewModel: PokemonDetailViewModel by activityViewModels()

    private val outerEvolutionAdapter by lazy {
        OuterEvolutionAdapter(activityViewModel).also { Timber.d("outerEvolutionAdapter lazy initialized") }
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
            rvPokemonDetailEvolutions.adapter = outerEvolutionAdapter
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
                            outerEvolutionAdapter.submitList(
                                listOf(
                                    evolutionsUiModel(depth = 0),
                                    evolutionsUiModel(depth = 1),
                                    evolutionsUiModel(depth = 2),
                                    evolutionsUiModel(depth = 3),
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}
