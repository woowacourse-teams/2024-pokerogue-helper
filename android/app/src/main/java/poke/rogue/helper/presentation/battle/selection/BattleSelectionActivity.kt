package poke.rogue.helper.presentation.battle.selection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBattleSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleActivity
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.BattleSelectionUiState
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.SelectionMode
import poke.rogue.helper.presentation.util.activity.hideKeyboard
import poke.rogue.helper.presentation.util.parcelable
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.serializable
import poke.rogue.helper.presentation.util.view.setImage

class BattleSelectionActivity :
    ErrorHandleActivity<ActivityBattleSelectionBinding>(R.layout.activity_battle_selection) {
    private val viewModel by viewModel<BattleSelectionViewModel> {
        parametersOf(selectionMode, previousSelection)
    }
    private val previousSelection by lazy {
        intent.parcelable<SelectionData>(KEY_PREVIOUS_SELECTION) ?: error("잘못된 선택 데이터")
    }
    private val selectionMode by lazy {
        intent.serializable<SelectionMode>(KEY_SELECTION_MODE) ?: error("잘못된 선택 데이터")
    }
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
        initListener()
        initObserver()
    }

    private fun initViews() {
        binding.lifecycleOwner = this
        binding.vm = viewModel

        with(binding.pagerBattleSelection) {
            adapter = selectionPagerAdapter
            isUserInputEnabled = false
        }
    }

    private fun initListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun initObserver() {
        repeatOnStarted {
            viewModel.selectedPokemon.collect { selectionState ->
                if (selectionState is BattleSelectionUiState.Selected) {
                    val selected = selectionState.content
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

        repeatOnStarted {
            viewModel.completeSelection.collect {
                handleSelectionResult(it)
            }
        }
    }

    private fun handleSelectionResult(result: SelectionData) {
        val intent = Intent().apply { putExtra(KEY_SELECTION_RESULT, result) }
        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        const val KEY_SELECTION_MODE = "selectionMode"
        const val KEY_PREVIOUS_SELECTION = "previousSelection"
        const val KEY_SELECTION_RESULT = "selectionResult"

        fun intent(
            context: Context,
            selectionMode: SelectionMode,
            previousSelection: SelectionData,
        ): Intent =
            Intent(context, BattleSelectionActivity::class.java).apply {
                putExtra(KEY_SELECTION_MODE, selectionMode)
                putExtra(KEY_PREVIOUS_SELECTION, previousSelection)
            }
    }
}
