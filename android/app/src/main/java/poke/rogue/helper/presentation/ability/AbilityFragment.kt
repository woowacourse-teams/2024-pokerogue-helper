package poke.rogue.helper.presentation.ability

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.datasource.RemoteAbilityDataSource
import poke.rogue.helper.data.repository.DefaultAbilityRepository
import poke.rogue.helper.databinding.FragmentAbilityBinding
import poke.rogue.helper.presentation.ability.detail.AbilityDetailFragment
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.remote.ServiceModule

class AbilityFragment :
    BindingFragment<FragmentAbilityBinding>(R.layout.fragment_ability) {
    private val viewModel by viewModels<AbilityViewModel> {
        AbilityViewModel.factory(
            DefaultAbilityRepository(
                remoteAbilityDataSource =
                    RemoteAbilityDataSource(
                        abilityService = ServiceModule.abilityService(),
                    ),
            ),
        )
    }

    private val adapter: AbilityAdapter by lazy { AbilityAdapter(viewModel) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        val decoration =
            LinearSpacingItemDecoration(spacing = 11.dp, true)
        binding.rvAbilityDescription.adapter = adapter
        binding.rvAbilityDescription.addItemDecoration(decoration)
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect { abilities ->
                when (abilities) {
                    is AbilityUiState.Loading -> {}
                    is AbilityUiState.Success -> {
                        adapter.submitList(abilities.data)
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.navigationToDetailEvent.collect { abilityId ->
                parentFragmentManager.commit {
                    val containerId = R.id.fragment_container_ability
                    replace<AbilityDetailFragment>(
                        containerId,
                        args =
                            AbilityDetailFragment.bundleOf(
                                abilityId,
                                containerId,
                            ),
                    )
                    addToBackStack(TAG)
                }
            }
        }
    }

    companion object {
        private val TAG: String = AbilityFragment::class.java.simpleName
    }
}
