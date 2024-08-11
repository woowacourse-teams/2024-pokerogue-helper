package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonStatBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.PokemonStatAdapter
import poke.rogue.helper.presentation.util.repeatOnStarted

class PokemonStatFragment : BindingFragment<FragmentPokemonStatBinding>(R.layout.fragment_pokemon_stat) {
    private val activityViewModel: PokemonDetailViewModel by activityViewModels()

    private val abilityAdapter by lazy { AbilityTitleAdapter(activityViewModel) }
    private val pokemonStatAdapter by lazy { PokemonStatAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun initAdapter() {
        binding.apply {
            rvPokemonStatsAbilities.adapter = abilityAdapter
            rvStatList.adapter = pokemonStatAdapter
        }
    }

    private fun bindDatas(uiState: PokemonDetailUiState.Success) {
        binding.apply {
            pokemonStatAdapter.submitList(uiState.stats)
            abilityAdapter.submitList(uiState.abilities)
        }
    }
}
