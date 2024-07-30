package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.datasource.RemotePokemonDetailDataSource
import poke.rogue.helper.data.repository.DefaultPokemonDetailRepository
import poke.rogue.helper.databinding.FragmentPokemonDetailBinding
import poke.rogue.helper.presentation.ability.detail.AbilityDetailFragment
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.PokemonStatAdapter
import poke.rogue.helper.presentation.dex.PokemonTypeAdapter
import poke.rogue.helper.presentation.util.fragment.stringOf
import poke.rogue.helper.presentation.util.fragment.toast
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.setImage
import poke.rogue.helper.remote.ServiceModule

class PokemonDetailFragment :
    BindingFragment<FragmentPokemonDetailBinding>(R.layout.fragment_pokemon_detail) {
    private val viewModel by viewModels<PokemonDetailViewModel> {
        PokemonDetailViewModel.factory(
            pokemonDetailRepository =
                DefaultPokemonDetailRepository(
                    remotePokemonDetailDataSource =
                        RemotePokemonDetailDataSource(
                            pokeDexService = ServiceModule.pokeDexService(),
                        ),
                ),
        )
    }
    private val abilityAdapter by lazy { AbilityTitleAdapter(viewModel) }
    private val pokemonTypeAdapter by lazy { PokemonTypeAdapter() }
    private val pokemonStatAdapter by lazy { PokemonStatAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        repeatOnStarted {
            viewModel.updatePokemonDetail(arguments?.getLong(POKEMON_ID))
        }
        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        with(binding) {
            rvTypeList.adapter = pokemonTypeAdapter
            rvStatList.adapter = pokemonStatAdapter
            rvPokemonAbilities.adapter = abilityAdapter
            rvPokemonAbilities.addItemDecoration(
                LinearSpacingItemDecoration(
                    spacing = 7.dp,
                    includeEdge = false,
                    orientation = LinearSpacingItemDecoration.Orientation.HORIZONTAL,
                ),
            )
        }
    }

    private fun initObservers() {
        observePokemonDetailUi()
        observeNavigateToAbilityDetailEvent()
    }

    private fun observePokemonDetailUi() {
        repeatOnStarted {
            viewModel.uiState.collect { pokemonDetail ->
                when (pokemonDetail) {
                    is PokemonDetailUiState.IsLoading -> return@collect
                    is PokemonDetailUiState.Success -> {
                        bindPokemonDetail(pokemonDetail)
                    }
                }
            }
        }
    }

    private fun observeNavigateToAbilityDetailEvent() {
        repeatOnStarted {
            viewModel.navigationToDetailEvent.collect { abilityId ->
                parentFragmentManager.commit {
                    val containerId = arguments?.getInt(CONTAINER_ID) ?: -1
                    if (containerId == -1) {
                        toast(R.string.dex_detail_error_containerId)
                        return@commit
                    }
                    replace<AbilityDetailFragment>(
                        containerId,
                        args = AbilityDetailFragment.bundleOf(abilityId, containerId),
                    )
                    addToBackStack(TAG)
                }
            }
        }
    }

    private fun bindPokemonDetail(pokemonDetail: PokemonDetailUiState.Success) {
        with(binding) {
            ivPokemon.setImage(pokemonDetail.pokemon.imageUrl)
            tvPokemonName.text =
                stringOf(
                    R.string.dex_poke_name_format,
                    pokemonDetail.pokemon.name,
                    pokemonDetail.pokemon.dexNumber,
                )
        }

        pokemonTypeAdapter.submitList(pokemonDetail.pokemon.types)
        pokemonStatAdapter.submitList(pokemonDetail.stats)
        abilityAdapter.submitList(pokemonDetail.abilities)
    }

    companion object {
        private const val POKEMON_ID = "pokemonId"
        private const val CONTAINER_ID = "containerId"
        val TAG: String = PokemonDetailFragment::class.java.simpleName

        fun bundleOf(
            pokemonId: Long,
            containerId: Int,
        ) = Bundle().apply {
            putLong(POKEMON_ID, pokemonId)
            putInt(CONTAINER_ID, containerId)
        }
    }
}
