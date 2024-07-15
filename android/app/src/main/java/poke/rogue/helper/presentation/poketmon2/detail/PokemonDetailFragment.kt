package poke.rogue.helper.presentation.poketmon2.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonDetailBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.poketmon2.PokemonStatAdapter
import poke.rogue.helper.presentation.poketmon2.PokemonTypeAdapter
import poke.rogue.helper.presentation.util.repeatOnStarted

class PokemonDetailFragment :
    BindingFragment<FragmentPokemonDetailBinding>(R.layout.fragment_pokemon_detail) {
    private val viewModel by viewModels<PokemonDetailViewModel>()
    private lateinit var pokemonTypeAdapter: PokemonTypeAdapter
    private lateinit var pokemonStatAdapter: PokemonStatAdapter

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
        binding.rvTypeList.apply {
            pokemonTypeAdapter = PokemonTypeAdapter()
            adapter = pokemonTypeAdapter
        }
        binding.rvStatList.apply {
            pokemonStatAdapter = PokemonStatAdapter()
            adapter = pokemonStatAdapter
        }
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect {
                pokemonTypeAdapter.submitList(it.types)
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
