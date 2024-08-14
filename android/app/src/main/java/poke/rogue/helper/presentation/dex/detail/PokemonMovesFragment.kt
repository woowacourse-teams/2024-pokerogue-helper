package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.divider.MaterialDividerItemDecoration.VERTICAL
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonMovesBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

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

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun initAdapter() {
        binding.rvPokemonDetailMoves.apply {
            adapter = movesAdapter
            val spacingItemDecoration =
                LinearSpacingItemDecoration(
                    spacing = 8.dp,
                    includeEdge = true,
                    orientation = LinearSpacingItemDecoration.Orientation.VERTICAL,
                )
            val dividerItemDecoration =
                MaterialDividerItemDecoration(
                    context,
                    VERTICAL,
                )
            addItemDecoration(spacingItemDecoration)
            addItemDecoration(dividerItemDecoration)
        }
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
