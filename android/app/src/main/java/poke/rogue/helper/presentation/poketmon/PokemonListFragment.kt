package poke.rogue.helper.presentation.poketmon

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonListBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration

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

    private fun initAdapter() {
        binding?.apply {
            // TODO: 화면 사이즈에 대응해서 spanCount 와 spacing 조정 여부
            val spanCount = 3
            val spacing = 7
            val includeEdge = false

            rvPokemonList.apply {
                adapter = pokemonAdapter
                layoutManager = GridLayoutManager(context, spanCount)
                addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
            }
        }
    }

    private fun initDummyPokemons() {
        pokemonAdapter.submitList(
            List(10) { PokemonUiModel.DUMMY },
        )
    }
}
