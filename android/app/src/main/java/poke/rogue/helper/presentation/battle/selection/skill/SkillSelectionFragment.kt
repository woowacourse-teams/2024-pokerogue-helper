package poke.rogue.helper.presentation.battle.selection.skill

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import kotlinx.coroutines.flow.collectLatest
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultBattleRepository
import poke.rogue.helper.databinding.FragmentSkillSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.util.fragment.hideKeyboard
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.setOnSearchAction

class SkillSelectionFragment :
    ErrorHandleFragment<FragmentSkillSelectionBinding>(R.layout.fragment_skill_selection) {
    private val sharedViewModel: BattleSelectionViewModel by activityViewModels()
    private val viewModel: SkillSelectionViewModel by viewModels<SkillSelectionViewModel> {
        SkillSelectionViewModel.factory(
            DefaultBattleRepository.instance(),
            sharedViewModel.previousSelection as? SelectionData.WithSkill,
        )
    }
    private val skillAdapter: SkillSelectionAdapter by lazy {
        SkillSelectionAdapter(viewModel)
    }

    override val errorViewModel: ErrorHandleViewModel
        get() = sharedViewModel
    override val toolbar: Toolbar?
        get() = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListener()
        initObserver()
    }

    private fun initViews() {
        with(binding.rvSkills) {
            adapter = skillAdapter
            addItemDecoration(
                LinearSpacingItemDecoration(spacing = 4.dp, false),
            )
        }
        binding.handler = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {
        binding.rvSkills.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
        binding.etSkillSelectionSearch.setOnSearchAction { hideKeyboard() }
    }

    private fun initObserver() {
        repeatOnStarted {
            sharedViewModel.pokemonSelectionUpdate.collect { newDexNumber ->
                if (newDexNumber != viewModel.previousPokemonDexNumber) {
                    viewModel.updateSkills(newDexNumber)
                } else {
                    viewModel.updatePreviousSkills(newDexNumber)
                }
            }
        }

        repeatOnStarted {
            viewModel.filteredSkills.collectLatest {
                skillAdapter.submitList(it)
            }
        }

        repeatOnStarted {
            viewModel.skillSelectedEvent.collect {
                hideKeyboard()
                sharedViewModel.selectSkill(it)
            }
        }
    }
}
