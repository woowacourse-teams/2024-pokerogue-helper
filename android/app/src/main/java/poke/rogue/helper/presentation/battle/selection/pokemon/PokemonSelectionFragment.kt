package poke.rogue.helper.presentation.battle.selection.pokemon

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.BattleSelectionUiState
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonSelectionFragment :
    ErrorHandleFragment<FragmentPokemonSelectionBinding>(R.layout.fragment_pokemon_selection) {
    private val sharedViewModel: BattleSelectionViewModel by activityViewModels()
    private val pokemonAdapter: PokemonSelectionAdapter by lazy {
        PokemonSelectionAdapter(sharedViewModel)
    }

    override val errorViewModel: ErrorHandleViewModel
        get() = sharedViewModel
    override val toolbar: Toolbar?
        get() = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initViews() {
        with(binding.rvPokemons) {
            adapter = pokemonAdapter
            addItemDecoration(
                LinearSpacingItemDecoration(spacing = 4.dp, false),
            )
        }
    }

    private fun initObserver() {
        repeatOnStarted {
            sharedViewModel.pokemons.collect {
                pokemonAdapter.submitList(it)
            }
        }

        repeatOnStarted {
            sharedViewModel.selectedPokemon.collect { selectionState ->
                if (selectionState is BattleSelectionUiState.Selected) {
                    val selected = selectionState.selected
                    pokemonAdapter.updateSelectedPokemon(selected.id)
                }
            }
        }
    }
}
