package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.datasource.RemotePokemonDetailDataSource
import poke.rogue.helper.data.repository.DefaultPokemonDetailRepository
import poke.rogue.helper.databinding.FragmentPokemonDetailBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.PokemonStatAdapter
import poke.rogue.helper.presentation.dex.PokemonTypeAdapter
import poke.rogue.helper.presentation.util.repeatOnStarted
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
        binding.rvTypeList.adapter = pokemonTypeAdapter
        binding.rvStatList.adapter = pokemonStatAdapter
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect { pokemonDetail ->
                when (pokemonDetail) {
                    is PokemonDetailUiState.IsLoading -> return@collect
                    is PokemonDetailUiState.PokemonDetailUiModel -> {
                        binding.ivPokemon.setImage(pokemonDetail.pokemon.imageUrl)
                        pokemonTypeAdapter.submitList(pokemonDetail.pokemon.types)
                        pokemonStatAdapter.submitList(pokemonDetail.stats)
                    }
                }
            }
        }
    }

    companion object {
        private const val POKEMON_ID = "pokemonId"

        fun bundleOf(pokemonId: Long) =
            Bundle().apply {
                putLong(POKEMON_ID, pokemonId)
            }
    }
}
