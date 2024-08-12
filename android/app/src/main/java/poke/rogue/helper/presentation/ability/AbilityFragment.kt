package poke.rogue.helper.presentation.ability

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultAbilityRepository
import poke.rogue.helper.databinding.FragmentAbilityBinding
import poke.rogue.helper.presentation.ability.detail.AbilityDetailFragment
import poke.rogue.helper.presentation.base.error.ErrorEvent
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.base.error.NetworkErrorActivity
import poke.rogue.helper.presentation.util.fragment.startActivity
import poke.rogue.helper.presentation.util.fragment.toast
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class AbilityFragment : ErrorHandleFragment<FragmentAbilityBinding>(R.layout.fragment_ability) {
    private val viewModel by viewModels<AbilityViewModel> {
        AbilityViewModel.factory(
            DefaultAbilityRepository.instance(),
        )
    }
    override val errorViewModel: ErrorHandleViewModel
        get() = viewModel

    private val adapter: AbilityAdapter by lazy { AbilityAdapter(viewModel) }

    override val toolbar: Toolbar?
        get() = binding.toolbarAbility

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

        repeatOnStarted {
            viewModel.commonErrorEvent.collect {
                when (it) {
                    is ErrorEvent.NetworkException -> startActivity<NetworkErrorActivity>()
                    is ErrorEvent.UnknownError, is ErrorEvent.HttpException -> {
                        toast(it.msg ?: getString(R.string.error_IO_Exception))
                    }
                }
            }
        }
    }

    companion object {
        private val TAG: String = AbilityFragment::class.java.simpleName
    }
}
