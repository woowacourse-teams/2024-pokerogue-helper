package poke.rogue.helper.presentation.battle.selection.pokemon

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonSelectionFragment :
    ErrorHandleFragment<FragmentPokemonSelectionBinding>(R.layout.fragment_pokemon_selection) {
    private val activityViewModel: BattleSelectionViewModel by activityViewModels()
    private val pokemonAdapter: PokemonSelectionAdapter by lazy {
        PokemonSelectionAdapter()
    }

    override val errorViewModel: ErrorHandleViewModel
        get() = activityViewModel
    override val toolbar: Toolbar?
        get() = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        pokemonAdapter.submitList(PokemonSelectionUiModel.DUMMY)
    }

    private fun initViews() {
        with(binding.rvPokemons) {
            adapter = pokemonAdapter
            addItemDecoration(
                LinearSpacingItemDecoration(spacing = 4.dp, false),
            )
        }
    }
}
