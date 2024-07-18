package poke.rogue.helper.presentation.type

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityTypeBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.result.TypeResultAdapter
import poke.rogue.helper.presentation.type.selection.TypeSelectionBottomSheetFragment
import poke.rogue.helper.presentation.util.context.drawableOf
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.setVisible

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

    private fun initViews() =
        with(binding) {
            setSupportActionBar(toolbarHome.toolbar)
            toolbarHome.toolbar.overflowIcon = drawableOf(R.drawable.ic_menu)
            supportActionBar?.setDisplayShowTitleEnabled(false)

            typeHandler = viewModel
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initAdapter()
        initObserver()
    }

    private fun initAdapter() {
        binding.rvTypeResult.adapter = typeResultAdapter
    }

    private fun initObserver() {
        repeatOnStarted {
            viewModel.typeEvent.collect {
                if (it is TypeEvent.ShowSelection) {
                    displayBottomSheet(it.selectorType)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.myType.collect { uiState ->
                binding.parallelogramTypeMyTypeContent.setVisible(uiState is TypeSelectionUiState.Selected)
                if (uiState is TypeSelectionUiState.Selected) {
                    binding.myType = uiState.selectedType
                }
            }
        }

        lifecycleScope.launch {
            viewModel.opponentType1.collect { uiState ->
                binding.parallelogramTypeOpponentTypeContent1.setVisible(uiState is TypeSelectionUiState.Selected)
                if (uiState is TypeSelectionUiState.Selected) {
                    binding.opponent1Type = uiState.selectedType
                }
            }
        }

        lifecycleScope.launch {
            viewModel.opponentType2.collect { uiState ->
                binding.parallelogramTypeOpponentTypeContent2.setVisible(uiState is TypeSelectionUiState.Selected)
                if (uiState is TypeSelectionUiState.Selected) {
                    binding.opponent2Type = uiState.selectedType
                }
            }
        }

        lifecycleScope.launch {
            viewModel.type.collect { matchedResult ->
                typeResultAdapter.submitList(matchedResult)
            }
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
