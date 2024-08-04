package poke.rogue.helper.presentation.type

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityTypeBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.result.TypeResultAdapter
import poke.rogue.helper.presentation.type.selection.TypeSelectionBottomSheetFragment
import poke.rogue.helper.presentation.util.context.drawableOf
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.repeatOnStarted

class TypeActivity : BindingActivity<ActivityTypeBinding>(R.layout.activity_type) {
    private val viewModel: TypeViewModel by viewModels {
        TypeViewModel.factory()
    }
    private val typeResultAdapter by lazy { TypeResultAdapter() }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_toolbar_pokerogue -> {
                navigateToPokeRogue()
            }

            R.id.item_toolbar_feedback -> {
            }

            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    private fun navigateToPokeRogue() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stringOf(R.string.home_pokerogue_url)))
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initAdapter()
        initObserver()
    }

    private fun initViews() =
        with(binding) {
            setSupportActionBar(toolbarHome.toolbar)
            toolbarHome.toolbar.overflowIcon = drawableOf(R.drawable.ic_menu)
            supportActionBar?.setDisplayShowTitleEnabled(false)

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