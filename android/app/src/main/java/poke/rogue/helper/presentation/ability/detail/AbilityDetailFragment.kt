package poke.rogue.helper.presentation.ability.detail

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.datasource.RemoteAbilityDataSource
import poke.rogue.helper.data.repository.DefaultAbilityRepository
import poke.rogue.helper.databinding.FragmentAbilityDetailBinding
import poke.rogue.helper.presentation.ability.model.toUi
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.detail.PokemonDetailFragment
import poke.rogue.helper.presentation.util.fragment.toast
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.remote.ServiceModule
import timber.log.Timber

class AbilityDetailFragment :
    BindingFragment<FragmentAbilityDetailBinding>(R.layout.fragment_ability_detail) {
    private val viewModel by viewModels<AbilityDetailViewModel> {
        AbilityDetailViewModel.factory(
            DefaultAbilityRepository(
                remoteAbilityDataSource = RemoteAbilityDataSource(
                    abilityService = ServiceModule.abilityService(),
                ),
            )
        )
    }

    private val adapter: AbilityDetailAdapter by lazy { AbilityDetailAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeatOnStarted {
            val id = arguments?.getLong(ABILITY_ID) ?: -1
            viewModel.updateAbilityDetail(id)
        }

        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        val decoration = GridSpacingItemDecoration(3, 15.dp, false)
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
                toast(R.string.ability_detail_error)
            }
        }

        repeatOnStarted {
            viewModel.navigationToPokemonDetailEvent.collect { pokemonId ->
                parentFragmentManager.commit {
                    replace<PokemonDetailFragment>(
                        R.id.fragment_container_ability,
                        args = PokemonDetailFragment.bundleOf(pokemonId),
                    )
                    addToBackStack(TAG)
                }
            }
        }
    }

    companion object {
        private const val ABILITY_ID = "abilityId"

        val TAG = AbilityDetailFragment::class.java.simpleName

        fun bundleOf(abilityId: Long) =
            Bundle().apply {
                putLong(ABILITY_ID, abilityId)
            }
    }
}
