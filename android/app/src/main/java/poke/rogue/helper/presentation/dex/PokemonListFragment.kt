package poke.rogue.helper.presentation.dex

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import poke.rogue.helper.R
import poke.rogue.helper.data.datasource.InMemoryPokemonListDataSource
import poke.rogue.helper.databinding.FragmentPokemonListBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.detail.PokemonDetailFragment
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonListFragment :
    BindingFragment<FragmentPokemonListBinding>(R.layout.fragment_pokemon_list) {
    private val viewModel by viewModels<PokemonListViewModel> {
        PokemonListViewModel.factory(
            pokemonListDataSource = InMemoryPokemonListDataSource(),
        )
    }

    private val pokemonAdapter: PokemonAdapter by lazy {
        PokemonAdapter(viewModel::onClickPokemon)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        binding.rvPokemonList.apply {
            val spanCount = 3
            adapter = pokemonAdapter
            layoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount = spanCount,
                    spacing = 7.dp,
                    includeEdge = false,
                ),
            )
        }
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect { pokemonUiModels ->
                pokemonAdapter.submitList(pokemonUiModels)
            }
        }
        repeatOnStarted {
            viewModel.navigateToDetailEvent.collect { pokemonId ->
                parentFragmentManager.commit {
                    replace<PokemonDetailFragment>(
                        R.id.fragment_container_pokemon,
                        args = PokemonDetailFragment.bundleOf(pokemonId),
                    )
                    addToBackStack(TAG)
                }
            }
        }
    }

    companion object {
        val TAG: String = PokemonListFragment::class.java.simpleName
    }
}
