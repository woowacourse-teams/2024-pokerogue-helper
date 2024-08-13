package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonMovesBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.util.repeatOnStarted

class PokemonMovesFragment : BindingFragment<FragmentPokemonMovesBinding>(R.layout.fragment_pokemon_moves) {
    private val activityViewModel: PokemonDetailViewModel by activityViewModels()

    private val movesAdapter: PokemonMovesAdapter by lazy { PokemonMovesAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        binding.rvPokemonDetailMoves.adapter = movesAdapter
    }

    private fun initObservers() {
        repeatOnStarted {
            activityViewModel.uiState.collect { state ->
                when (state) {
                    is PokemonDetailUiState.IsLoading -> {}
                    is PokemonDetailUiState.Success -> movesAdapter.submitList(state.moves)
                }
            }
        }
    }
}
