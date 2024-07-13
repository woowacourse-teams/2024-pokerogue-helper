package poke.rogue.helper.presentation.poketmon

import android.os.Bundle
import android.view.View
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonListBinding
import poke.rogue.helper.presentation.base.BindingFragment

class PokemonListFragment : BindingFragment<FragmentPokemonListBinding>(R.layout.fragment_pokemon_list) {
    private val pokemonAdapter by lazy { PokemonAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initDummyPokemons()
    }

    // TODO: remove null-assertion
    private fun initAdapter() {
        binding?.apply {
            rvPokemonList.adapter = pokemonAdapter
        }
    }

    private fun initDummyPokemons() {
        pokemonAdapter.submitList(
            List(10) { PokemonUiModel.DUMMY },
        )
    }
}
