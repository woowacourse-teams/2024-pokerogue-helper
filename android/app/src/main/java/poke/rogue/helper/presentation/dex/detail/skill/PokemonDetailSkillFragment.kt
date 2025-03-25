package poke.rogue.helper.presentation.dex.detail.skill

import android.os.Bundle
import android.view.View
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.divider.MaterialDividerItemDecoration.VERTICAL
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonSkillsBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.detail.PokemonDetailUiState
import poke.rogue.helper.presentation.dex.detail.PokemonDetailViewModel
import poke.rogue.helper.presentation.dex.model.SkillListItem
import poke.rogue.helper.presentation.dex.model.toUi
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonDetailSkillFragment :
    BindingFragment<FragmentPokemonSkillsBinding>(R.layout.fragment_pokemon_skills) {
    private val activityViewModel: PokemonDetailViewModel by activityViewModel<PokemonDetailViewModel>()

    private val eggSkillsAdapter: NewPokemonDetailSkillAdapter by lazy { NewPokemonDetailSkillAdapter() }
    private val skillsAdapter: PokemonDetailSkillAdapter by lazy { PokemonDetailSkillAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()
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

        binding.rvPokemonDetailEggSkills.apply {
            adapter = eggSkillsAdapter

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
                    is PokemonDetailUiState.Success -> {
                        eggSkillsAdapter.submitList(
                            buildList {
                                add(SkillListItem.Header)
                                addAll(state.skills.eggLearn.toUi())
                            },
                        )

                        skillsAdapter.submitList(state.skills.selfLearn.toUi())
                    }
                }
            }
        }
    }
}
