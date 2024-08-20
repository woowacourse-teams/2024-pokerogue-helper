package poke.rogue.helper.presentation.battle.selection.pokemon

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonSelectionFragment :
    ErrorHandleFragment<FragmentPokemonSelectionBinding>(R.layout.fragment_pokemon_selection) {
    private val sharedViewModel: BattleSelectionViewModel by activityViewModels()
    private val viewModel: PokemonSelectionViewModel by viewModels<PokemonSelectionViewModel>()
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
            viewModel.pokemons.collect {
                pokemonAdapter.submitList(it)
            }
        }

        repeatOnStarted {
            viewModel.pokemonSelectedEvent.collect {
                sharedViewModel.selectPokemon(it)
            }
        }
    }
}
