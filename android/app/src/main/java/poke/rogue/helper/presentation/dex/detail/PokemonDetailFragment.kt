package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.datasource.RemotePokemonDetailDataSource
import poke.rogue.helper.data.repository.DefaultPokemonDetailRepository
import poke.rogue.helper.databinding.FragmentPokemonDetailBinding
import poke.rogue.helper.presentation.ability.detail.AbilityDetailActivity
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.PokemonStatAdapter
import poke.rogue.helper.presentation.dex.PokemonTypeAdapter
import poke.rogue.helper.presentation.dex.model.AbilityTitleUiModel
import poke.rogue.helper.presentation.util.fragment.stringOf
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.setImage
import poke.rogue.helper.remote.ServiceModule
import timber.log.Timber

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
                    is PokemonDetailUiState.PokemonDetailUiModel -> {
                        binPokemonDetail(pokemonDetail)
                    }
                }
            }
        }
    }

    private fun observeNavigateToAbilityDetailEvent() {
        repeatOnStarted {
            viewModel.navigationToDetailEvent.collect { abilityId ->
                AbilityDetailActivity.intent(requireContext(), abilityId).also { startActivity(it) }
            }
        }
    }

    private fun binPokemonDetail(pokemonDetail: PokemonDetailUiState.PokemonDetailUiModel) {
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
        abilityAdapter.submitList(
            pokemonDetail.abilities.map { AbilityTitleUiModel(1, it) }.also {
                Timber.d("pokemon Abilities: $it")
            },
        )
    }

    companion object {
        private const val POKEMON_ID = "pokemonId"

        fun bundleOf(pokemonId: Long) =
            Bundle().apply {
                putLong(POKEMON_ID, pokemonId)
            }
    }
}
