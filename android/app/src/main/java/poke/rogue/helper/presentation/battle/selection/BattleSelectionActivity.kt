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
        initViews()
        initObserver()
    }

    private fun initViews() {
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.pagerBattleSelection.adapter = selectionPagerAdapter
        binding.pagerBattleSelection.isUserInputEnabled = false
    }

    private fun initObserver() {
        repeatOnStarted {
            viewModel.selectedPokemon.collect { selectionState ->
                if (selectionState is BattleSelectionUiState.Selected) {
                    val selected = selectionState.selected
                    binding.ivPokemon.setImage(selected.frontImageUrl)
                    binding.toolbarBattleSelection.title = selected.name
                }
            }
        }

        repeatOnStarted {
            viewModel.currentStep.collect {
                binding.pagerBattleSelection.currentItem = it.ordinal
            }
        }
    }
}
