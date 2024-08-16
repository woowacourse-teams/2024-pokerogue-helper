package poke.rogue.helper.presentation.battle.selection

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel

class PokemonSelectionFragment :
    ErrorHandleFragment<FragmentPokemonSelectionBinding>(R.layout.fragment_pokemon_selection) {
    private val activityViewModel: BattleSelectionViewModel by activityViewModels()

    override val errorViewModel: ErrorHandleViewModel
        get() = activityViewModel
    override val toolbar: Toolbar?
        get() = null
}
