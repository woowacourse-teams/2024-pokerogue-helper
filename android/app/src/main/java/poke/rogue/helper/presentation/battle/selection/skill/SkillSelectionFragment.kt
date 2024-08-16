package poke.rogue.helper.presentation.battle.selection.skill

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentSkillSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class SkillSelectionFragment :
    ErrorHandleFragment<FragmentSkillSelectionBinding>(R.layout.fragment_skill_selection) {
    private val activityViewModel: BattleSelectionViewModel by activityViewModels()
    private val skillAdapter: SkillSelectionAdapter by lazy {
        SkillSelectionAdapter()
    }

    override val errorViewModel: ErrorHandleViewModel
        get() = activityViewModel
    override val toolbar: Toolbar?
        get() = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
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
}
