package poke.rogue.helper.presentation.battle.selection.skill

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.flow.collect
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentSkillSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class SkillSelectionFragment :
    ErrorHandleFragment<FragmentSkillSelectionBinding>(R.layout.fragment_skill_selection) {
    private val sharedViewModel: BattleSelectionViewModel by activityViewModels()
    private val skillAdapter: SkillSelectionAdapter by lazy {
        SkillSelectionAdapter(sharedViewModel)
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
        initObserver()
        skillAdapter.submitList(SkillSelectionUiModel.DUMMY)
    }

    private fun initViews() {
        with(binding.rvSkills) {
            adapter = skillAdapter
            addItemDecoration(
                LinearSpacingItemDecoration(spacing = 4.dp, false),
            )
        }
    }

    private fun initObserver() {
        repeatOnStarted {
            sharedViewModel.skills.collect {
                skillAdapter.submitList(it)
            }
        }

        repeatOnStarted {
            sharedViewModel.selectedSkill.collect {
                it?.let { skillAdapter.updateSelectedSkill(it.id) }
            }
        }
    }
}
