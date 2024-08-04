package poke.rogue.helper.presentation.type

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityTypeBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.result.TypeResultAdapter
import poke.rogue.helper.presentation.type.selection.TypeSelectionBottomSheetFragment
import poke.rogue.helper.presentation.util.repeatOnStarted

class TypeActivity : BindingActivity<ActivityTypeBinding>(R.layout.activity_type) {
    private val viewModel: TypeViewModel by viewModels {
        TypeViewModel.factory()
    }
    private val typeResultAdapter by lazy { TypeResultAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initAdapter()
        initObserver()
    }

    override fun toolBar(): Toolbar = binding.toolbarType

    private fun initViews() =
        with(binding) {
            typeHandler = viewModel
            vm = viewModel
            lifecycleOwner = this@TypeActivity
        }

    private fun initAdapter() {
        binding.rvTypeResult.adapter = typeResultAdapter
    }

    private fun initObserver() {
        observeTypeEvent()
        observeTypeSelectionStates()
        observeTypeResults()
    }

    private fun observeTypeEvent() {
        repeatOnStarted {
            viewModel.typeEvent.collect {
                if (it is TypeEvent.ShowSelection) {
                    showBottomSheet(it.selectorType, it.disabledTypes)
                }
            }
        }
    }

    private fun observeTypeSelectionStates() {
        repeatOnStarted {
            viewModel.typeSelectionStates.collect { states ->
                if (states.myType is TypeSelectionUiState.Selected) {
                    binding.myType = states.myType.selectedType
                }

                if (states.opponentType1 is TypeSelectionUiState.Selected) {
                    binding.opponent1Type = states.opponentType1.selectedType
                }

                if (states.opponentType2 is TypeSelectionUiState.Selected) {
                    binding.opponent2Type = states.opponentType2.selectedType
                }
            }
        }
    }

    private fun observeTypeResults() {
        repeatOnStarted {
            viewModel.type.collect { matchedResult ->
                typeResultAdapter.submitList(matchedResult)
            }
        }
    }

    private fun showBottomSheet(
        selectorType: SelectorType,
        disabledTypes: Set<TypeUiModel>,
    ) {
        TypeSelectionBottomSheetFragment.newInstance(selectorType, disabledTypes).show(
            supportFragmentManager,
            TypeSelectionBottomSheetFragment.TAG,
        )
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, TypeActivity::class.java)
        }
    }
}
