package poke.rogue.helper.presentation.ability.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.datasource.RemoteAbilityDataSource
import poke.rogue.helper.data.repository.DefaultAbilityRepository
import poke.rogue.helper.databinding.FragmentAbilityDetailBinding
import poke.rogue.helper.presentation.ability.model.toUi
import poke.rogue.helper.presentation.dex.detail.PokemonDetailFragment
import poke.rogue.helper.presentation.toolbar.ToolbarFragment
import poke.rogue.helper.presentation.util.fragment.toast
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.remote.ServiceModule

class AbilityDetailFragment :
    ToolbarFragment<FragmentAbilityDetailBinding>(R.layout.fragment_ability_detail) {
    private val viewModel by viewModels<AbilityDetailViewModel> {
        AbilityDetailViewModel.factory(
            DefaultAbilityRepository(
                remoteAbilityDataSource =
                RemoteAbilityDataSource(
                    abilityService = ServiceModule.abilityService(),
                ),
            ),
        )
    }

    private val adapter: AbilityDetailAdapter by lazy { AbilityDetailAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getLong(ABILITY_ID) ?: INVALID_ABILITY_ID
        viewModel.updateAbilityDetail(id)
    }

    override val toolbar: Toolbar?
        get() = binding.toolbarAbilityDetail

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        val decoration = GridSpacingItemDecoration(3, 15.dp, true)
        binding.rvAbilityDetailPokemon.adapter = adapter
        binding.rvAbilityDetailPokemon.addItemDecoration(decoration)
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.abilityDetail.collect { abilityDetail ->
                when (abilityDetail) {
                    is AbilityDetailUiState.Loading -> {}
                    is AbilityDetailUiState.Success -> {
                        binding.abilityUiModel = abilityDetail.data.toUi()
                        adapter.submitList(abilityDetail.data.pokemons)
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.errorEvent.collect {
                toast(R.string.ability_detail_error_abilityId)
            }
        }

        repeatOnStarted {
            viewModel.navigationToPokemonDetailEvent.collect { pokemonId ->
                parentFragmentManager.commit {
                    val containerId = arguments?.getInt(CONTAINER_ID) ?: INVALID_CONTAINER_ID
                    if (containerId == INVALID_CONTAINER_ID) {
                        toast(R.string.ability_detail_error_containerId)
                        return@commit
                    }
                    replace<PokemonDetailFragment>(
                        containerId,
                        args = PokemonDetailFragment.bundleOf(pokemonId, containerId),
                    )
                    addToBackStack(TAG)
                }
            }
        }
    }

    companion object {
        private const val ABILITY_ID = "abilityId"
        private const val CONTAINER_ID = "containerId"
        private const val INVALID_ABILITY_ID = -1L
        private const val INVALID_CONTAINER_ID = -1
        private val TAG = AbilityDetailFragment::class.java.simpleName

        fun bundleOf(
            abilityId: Long,
            containerId: Int,
        ) = Bundle().apply {
            putLong(ABILITY_ID, abilityId)
            putInt(CONTAINER_ID, containerId)
        }
    }
}
