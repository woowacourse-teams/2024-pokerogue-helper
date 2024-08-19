package poke.rogue.helper.presentation.dex.detail.skill

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.divider.MaterialDividerItemDecoration.VERTICAL
import poke.rogue.helper.R
import poke.rogue.helper.data.model.PokemonSkill
import poke.rogue.helper.databinding.FragmentPokemonSkillsBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.detail.PokemonDetailUiState
import poke.rogue.helper.presentation.dex.detail.PokemonDetailViewModel
import poke.rogue.helper.presentation.dex.model.PokemonSkillUiModel
import poke.rogue.helper.presentation.dex.model.toUi
import poke.rogue.helper.presentation.type.model.toUi
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonDetailSkillFragment : BindingFragment<FragmentPokemonSkillsBinding>(R.layout.fragment_pokemon_skills) {
    private val activityViewModel: PokemonDetailViewModel by activityViewModels()

    private val skillsAdapter: PokemonDetailSkillAdapter by lazy { PokemonDetailSkillAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun initAdapter() {
        binding.rvPokemonDetailSkills.apply {
            adapter = skillsAdapter
            val spacingItemDecoration =
                LinearSpacingItemDecoration(
                    spacing = 8.dp,
                    includeEdge = true,
                    orientation = LinearSpacingItemDecoration.Orientation.VERTICAL,
                )
            val dividerItemDecoration =
                MaterialDividerItemDecoration(
                    context,
                    VERTICAL,
                )
            addItemDecoration(spacingItemDecoration)
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun initObservers() {
        repeatOnStarted {
            activityViewModel.uiState.collect { state ->
                when (state) {
                    is PokemonDetailUiState.IsLoading -> {}
                    // TODO: skill 을 현재는 한 종류의 스킬 목록만 사용하고 있음..... 이후에는 여러개의 스킬을 받아야함
                    is PokemonDetailUiState.Success -> skillsAdapter.submitList(state.skills.selfLearn.toUi())
                }
            }
        }
    }
}

// TODO: skill 을 현재는 한 종류의 스킬 목록만 사용하고 있음..... 이후에는 여러개의 스킬을 받아야함
fun PokemonSkill.toUi(): PokemonSkillUiModel =
    PokemonSkillUiModel(
        id = id,
        name = name,
        level = level,
        power = if (power == PokemonSkill.NO_POWER_VALUE) PokemonSkillUiModel.NO_POWER else power.toString(),
        type = type.toUi(),
        accuracy = accuracy,
        category = category.toUi(),
    )

fun List<PokemonSkill>.toUi(): List<PokemonSkillUiModel> = map(PokemonSkill::toUi)
