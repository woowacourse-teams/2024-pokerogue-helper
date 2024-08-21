package poke.rogue.helper.presentation.dex.detail.information

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.model.PokemonBiome
import poke.rogue.helper.databinding.FragmentPokemonInformationBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.detail.PokemonDetailUiState
import poke.rogue.helper.presentation.dex.detail.PokemonDetailViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonInformationFragment :
    BindingFragment<FragmentPokemonInformationBinding>(R.layout.fragment_pokemon_information) {
    private val activityViewModel: PokemonDetailViewModel by activityViewModels()
    private val biomesAdapter: PokemonDetailBiomeAdapter by lazy { PokemonDetailBiomeAdapter(activityViewModel) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initObserver()
    }

    private fun initAdapter() {
        binding.rvPokemonDetailInformation.apply {
            adapter = biomesAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 9.dp, false))
        }
    }

    private fun initObserver() {
        repeatOnStarted {
            activityViewModel.uiState.collect {
                when (it) {
                    is PokemonDetailUiState.IsLoading -> {}
                    is PokemonDetailUiState.Success -> biomesAdapter.submitList(PokemonBiome.DUMMYS)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}
