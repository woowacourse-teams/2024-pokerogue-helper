package poke.rogue.helper.presentation.battle.selection

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBattleSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleActivity
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel

class BattleSelectionActivity :
    ErrorHandleActivity<ActivityBattleSelectionBinding>(R.layout.activity_battle_selection) {
    private val viewModel by viewModels<BattleSelectionViewModel>()
    private val selectionPagerAdapter: BattleSelectionPagerAdapter by lazy {
        BattleSelectionPagerAdapter(this)
    }

    override val errorViewModel: ErrorHandleViewModel
        get() = viewModel

    override val toolbar: Toolbar
        get() = binding.toolbarBattleSelection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.pagerBattleSelection.adapter = selectionPagerAdapter
    }
}
