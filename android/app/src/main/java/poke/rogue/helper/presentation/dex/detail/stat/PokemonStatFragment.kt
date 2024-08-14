package poke.rogue.helper.presentation.dex.detail.stat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonStatBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.detail.PokemonDetailUiState
import poke.rogue.helper.presentation.dex.detail.PokemonDetailViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonStatFragment : BindingFragment<FragmentPokemonStatBinding>(R.layout.fragment_pokemon_stat) {
    private val activityViewModel: PokemonDetailViewModel by activityViewModels()

    private val abilityAdapter by lazy { AbilityTitleAdapter(activityViewModel) }
    private val pokemonStatAdapter by lazy { PokemonStatAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.eventHandler = activityViewModel

        initAdapter()
        repeatOnStarted {
            activityViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is PokemonDetailUiState.IsLoading -> return@collect
                    is PokemonDetailUiState.Success -> bindDatas(uiState)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun initAdapter() {
        binding.apply {
            rvPokemonAbilities.adapter = abilityAdapter
            rvPokemonStats.adapter = pokemonStatAdapter

            rvPokemonAbilities.addItemDecoration(
                LinearSpacingItemDecoration(
                    spacing = 7.dp,
                    includeEdge = false,
                    orientation = LinearSpacingItemDecoration.Orientation.HORIZONTAL,
                ),
            )
        }
    }

    private fun bindDatas(uiState: PokemonDetailUiState.Success) {
        binding.apply {
            pokemonStatAdapter.submitList(uiState.stats)
            abilityAdapter.submitList(uiState.abilities)
        }
    }
}
