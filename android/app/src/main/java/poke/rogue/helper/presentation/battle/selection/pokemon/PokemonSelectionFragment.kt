package poke.rogue.helper.presentation.battle.selection.pokemon

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.selectedPokemon
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.util.fragment.hideKeyboard
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.setOnSearchAction

class PokemonSelectionFragment :
    ErrorHandleFragment<FragmentPokemonSelectionBinding>(R.layout.fragment_pokemon_selection) {
    private val sharedViewModel: BattleSelectionViewModel by activityViewModel()
    private val viewModel: PokemonSelectionViewModel by viewModel<PokemonSelectionViewModel> {
        parametersOf(sharedViewModel.previousSelection.selectedPokemon())
    }
    private val pokemonAdapter: PokemonSelectionAdapter by lazy {
        PokemonSelectionAdapter(viewModel)
    }

    override val errorViewModel: ErrorHandleViewModel
        get() = viewModel
    override val toolbar: Toolbar?
        get() = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListener()
        initObserver()
    }

    private fun initViews() {
        binding.handler = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        with(binding.rvPokemons) {
            adapter = pokemonAdapter
            addItemDecoration(
                LinearSpacingItemDecoration(spacing = 4.dp, false),
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {
        binding.rvPokemons.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }

        binding.etPokemonSelectionSearch.setOnSearchAction { hideKeyboard() }
    }

    private fun initObserver() {
        repeatOnStarted {
            viewModel.filteredPokemon.collect {
                pokemonAdapter.submitList(it) {
                    if (sharedViewModel.previousSelection.selectedPokemon() != null) {
                        val position = it.indexOfFirst { it.isSelected }
                        binding.rvPokemons.scrollToPosition(position)
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.pokemonSelectedEvent.collect {
                hideKeyboard()
                sharedViewModel.selectPokemon(it)
            }
        }
    }
}
