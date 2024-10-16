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

class PokemonEvolutionFragment : BindingFragment<FragmentPokemonEvolutionBinding>(R.layout.fragment_pokemon_evolution) {
    private val activityViewModel: PokemonDetailViewModel by activityViewModels()

    //    private val evolutionDepth0Adapter by lazy { EvolutionAdapter(activityViewModel) }
//    private val evolutionDepth1Adapter by lazy { EvolutionAdapter(activityViewModel) }
//    private val evolutionDepth2Adapter by lazy { EvolutionAdapter(activityViewModel) }
//    private val evolutionDepth3Adapter by lazy { EvolutionAdapter(activityViewModel) }
    private val outerEvolutionAdapter by lazy { OuterEvolutionAdapter(activityViewModel) }

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
//            rvPokemonEvolutionDepth0.recyclerView.adapter = evolutionDepth0Adapter
//            rvPokemonEvolutionDepth1.recyclerView.adapter = evolutionDepth1Adapter
//            rvPokemonEvolutionDepth2.recyclerView.adapter = evolutionDepth2Adapter
//            rvPokemonEvolutionDepth3.recyclerView.adapter = evolutionDepth3Adapter

        }
    }

    private fun initObserver() {
        repeatOnStarted {
            activityViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is PokemonDetailUiState.IsLoading -> {}
                    is PokemonDetailUiState.Success -> {
                        binding.evolutions = uiState.evolutions
//                        uiState.evolutions.apply {
//                            evolutions(depth = 0).let(evolutionDepth0Adapter::submitList)
//                            evolutions(depth = 1).let(evolutionDepth1Adapter::submitList)
//                            evolutions(depth = 2).let(evolutionDepth2Adapter::submitList)
//                            evolutions(depth = 3).let(evolutionDepth3Adapter::submitList)
//                        }
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
