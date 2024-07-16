package poke.rogue.helper.presentation.type

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityTypeBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeSelectionUiState
import poke.rogue.helper.presentation.type.typeselection.TypeSelectionBottomSheetFragment
import poke.rogue.helper.presentation.util.context.colorOf

class TypeActivity : BindingActivity<ActivityTypeBinding>(R.layout.activity_type) {
    private val viewModel: TypeViewModel by viewModels {
        TypeViewModel.factory()
    }
    private val typeResultAdapter by lazy { TypeResultAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initListener()
        initObserver()
        binding.typeHandler = viewModel
    }

    private fun initAdapter() {
        binding.rvTypeResult.adapter = typeResultAdapter
    }

    private fun initListener() {
        binding.vwTypeMyTypeContainer.setOnClickListener {
            displayBottomSheet(SelectorType.MINE)
        }

        binding.vwTypeOpponentTypeContainer1.setOnClickListener {
            displayBottomSheet(SelectorType.OPPONENT1)
        }

        binding.vwTypeOpponentTypeContainer2.setOnClickListener {
            displayBottomSheet(SelectorType.OPPONENT2)
        }
    }

    private fun initObserver() {
        viewModel.myType.observe(this) { uiState ->
            when (uiState) {
                is TypeSelectionUiState.Selected -> {
                    val data = uiState.selectedType
                    binding.vwTypeMyTypeContent.visibility = View.VISIBLE
                    binding.vwTypeMyTypeContent.setContentColor(this.colorOf(data.typeColor))
                    binding.ivTypeMyTypeContent.setImageResource(uiState.selectedType.typeIconResId)
                }

                is TypeSelectionUiState.Idle -> {
                    binding.vwTypeMyTypeContent.visibility = View.GONE
                }
            }
        }

        viewModel.opponentType1.observe(this) { uiState ->
            when (uiState) {
                is TypeSelectionUiState.Selected -> {
                    val data = uiState.selectedType
                    binding.vwTypeOpponentTypeContent1.visibility = View.VISIBLE
                    binding.vwTypeOpponentTypeContent1.setContentColor(this.colorOf(data.typeColor))
                    binding.ivTypeOpponentTypeContent1.setImageResource(uiState.selectedType.typeIconResId)
                }

                is TypeSelectionUiState.Idle -> {
                    binding.vwTypeOpponentTypeContent1.visibility = View.GONE
                }
            }
        }

        viewModel.opponentType2.observe(this) { uiState ->
            when (uiState) {
                is TypeSelectionUiState.Selected -> {
                    val data = uiState.selectedType
                    binding.vwTypeOpponentTypeContent2.visibility = View.VISIBLE
                    binding.vwTypeOpponentTypeContent2.setContentColor(this.colorOf(data.typeColor))
                    binding.ivTypeOpponentTypeContent2.setImageResource(uiState.selectedType.typeIconResId)
                }

                is TypeSelectionUiState.Idle -> {
                    binding.vwTypeOpponentTypeContent2.visibility = View.GONE
                }
            }
        }

        viewModel.type.observe(this) { matchedResult ->
            typeResultAdapter.submitList(matchedResult)
        }
    }

    private fun displayBottomSheet(selectorType: SelectorType) {
        TypeSelectionBottomSheetFragment.newInstance(selectorType).show(
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
