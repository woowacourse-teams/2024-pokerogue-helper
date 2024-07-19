package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.datasource.FakePokemonDetailDataSource
import poke.rogue.helper.data.repository.FakePokemonDetailRepository
import poke.rogue.helper.databinding.FragmentPokemonDetailBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.PokemonStatAdapter
import poke.rogue.helper.presentation.dex.PokemonTypeAdapter
import poke.rogue.helper.presentation.util.repeatOnStarted

class PokemonDetailFragment :
    BindingFragment<FragmentPokemonDetailBinding>(R.layout.fragment_pokemon_detail) {
    private val viewModel by viewModels<PokemonDetailViewModel> {
        PokemonDetailViewModel.factory(
            pokemonDetailRepository =
                FakePokemonDetailRepository(
                    fakePokemonDetailDataSource = FakePokemonDetailDataSource(),
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
        viewModel.updatePokemonDetail(arguments?.getLong(POKEMON_ID))
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        binding.rvTypeList.adapter = pokemonTypeAdapter
        binding.rvStatList.adapter = pokemonStatAdapter
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect {
                pokemonTypeAdapter.submitList(it.pokemon.types)
                pokemonStatAdapter.submitList(it.stats)
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
