package poke.rogue.helper.presentation.battle.selection.skill

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultBattleRepository
import poke.rogue.helper.databinding.FragmentSkillSelectionBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.selectedPokemonOrNull
import poke.rogue.helper.presentation.battle.selectedData
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

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

    private fun initObserver() {
        repeatOnStarted {
            sharedViewModel.selectedPokemon.collect {
                val dexNumber = it.selectedData()?.dexNumber
                val selectedPokemonId = it.selectedData()?.id
                val previousSelectedPokemonId =
                    sharedViewModel.previousSelection.selectedPokemonOrNull()?.id

                if (dexNumber != null && previousSelectedPokemonId != selectedPokemonId) {
                    viewModel.updateSkills(dexNumber)
                }
            }
        }

//        repeatOnStarted {
//            viewModel.skills.collect {
//                skillAdapter.submitList(it)
//            }
//        }

        repeatOnStarted {
            viewModel.filteredSkills.collect {
                skillAdapter.submitList(it)
            }
        }

        repeatOnStarted {
            viewModel.skillSelectedEvent.collect {
                sharedViewModel.selectSkill(it)
            }
        }
    }
}
