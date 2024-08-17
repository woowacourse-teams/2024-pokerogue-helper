package poke.rogue.helper.presentation.battle.selection

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBattleSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleActivity
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.setImage

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
        binding.lifecycleOwner = this
        initViews()
        initObserver()
    }

    private fun initViews() {
        binding.pagerBattleSelection.adapter = selectionPagerAdapter
    }

    private fun initObserver() {
        repeatOnStarted {
            viewModel.selectedPokemon.collect {
                it?.let {
                    binding.ivPokemon.setImage(it.frontImageUrl)
                    binding.toolbarBattleSelection.title = it.name
                }
            }
        }
    }
}
