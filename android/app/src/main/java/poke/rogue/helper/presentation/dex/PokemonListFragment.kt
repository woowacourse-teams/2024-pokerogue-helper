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
import poke.rogue.helper.presentation.dex.detail.PokemonDetailActivity
import poke.rogue.helper.presentation.error.ErrorEvent
import poke.rogue.helper.presentation.error.NetworkErrorActivity
import poke.rogue.helper.presentation.toolbar.ToolbarFragment
import poke.rogue.helper.presentation.util.fragment.startActivity
import poke.rogue.helper.presentation.util.fragment.toast
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonListFragment :
    ToolbarFragment<FragmentPokemonListBinding>(R.layout.fragment_pokemon_list) {
    private val viewModel by viewModels<PokemonListViewModel> {
        PokemonListViewModel.factory(
            DefaultDexRepository.instance(),
        )
    }

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
            val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
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
        observeDisplayedPokemons()
        observeNavigateToDetail()
    }

    private fun observeDisplayedPokemons() {
        repeatOnStarted {
            viewModel.uiState.collect { pokemonUiModels ->
                pokemonAdapter.submitList(pokemonUiModels)
            }
        }
    }

    private fun observeNavigateToDetail() {
        repeatOnStarted {
            viewModel.navigateToDetailEvent.collect { pokemonId ->
                parentFragmentManager.commit {
                    startActivity(PokemonDetailActivity.intent(requireContext(), pokemonId))
                }
            }
        }
        repeatOnStarted {
            viewModel.commonErrorEvent.collect {
                when (it) {
                    is ErrorEvent.NetworkException -> startActivity<NetworkErrorActivity>()
                    is ErrorEvent.UnknownError, is ErrorEvent.HttpException -> {
                        toast(it.msg ?: getString(R.string.error_IO_Exception))
                    }
                }
            }
        }
    }

    companion object {
        val TAG: String = PokemonListFragment::class.java.simpleName
    }
}
