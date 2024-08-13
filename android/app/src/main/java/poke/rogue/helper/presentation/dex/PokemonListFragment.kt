package poke.rogue.helper.presentation.dex

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.databinding.FragmentPokemonListBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleFragment
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.dex.detail.PokemonDetailActivity
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonListFragment :
    ErrorHandleFragment<FragmentPokemonListBinding>(R.layout.fragment_pokemon_list) {
    private val viewModel by viewModels<PokemonListViewModel> {
        PokemonListViewModel.factory(
            DefaultDexRepository.instance(),
        )
    }
    override val errorViewModel: ErrorHandleViewModel
        get() = viewModel

    private val pokemonAdapter: PokemonAdapter by lazy {
        PokemonAdapter(viewModel)
    }

    override val toolbar: Toolbar
        get() = binding.toolbarDex

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        binding.rvPokemonList.apply {
            val spanCount =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
            adapter = pokemonAdapter
            layoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount = spanCount,
                    spacing = 9.dp,
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
                    startActivity(PokemonDetailActivity.intent(requireContext(), pokemonId))
                }
            }
        }
    }

    companion object {
        val TAG: String = PokemonListFragment::class.java.simpleName
    }
}
