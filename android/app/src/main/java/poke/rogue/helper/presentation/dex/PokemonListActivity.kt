package poke.rogue.helper.presentation.dex

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.databinding.ActivityPokemonListBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleActivity
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.dex.detail.PokemonDetailActivity
import poke.rogue.helper.presentation.dex.filter.PokeFilterUiModel
import poke.rogue.helper.presentation.dex.filter.PokemonFilterBottomSheetFragment
import poke.rogue.helper.presentation.util.activity.hideKeyboard
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.ui.component.PokeChip
import poke.rogue.helper.ui.component.PokeChip.Companion.bindPokeChip
import poke.rogue.helper.ui.layout.PaddingValues

class PokemonListActivity :
    ErrorHandleActivity<ActivityPokemonListBinding>(R.layout.activity_pokemon_list) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        initAdapter()
        initObservers()
        initListeners()
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
            viewModel.uiState.collect { uiState ->
                pokemonAdapter.submitList(uiState.pokemons)
                binding.chipPokeFiter.bindPokeChip(
                    PokeChip.Spec(
                        label = stringOf(
                            R.string.dex_filter_chip,
                            if (uiState.isFiltered) uiState.filterCount.toString() else ""
                        ),
                        trailingIconRes = R.drawable.ic_filter,
                        isSelected = uiState.isFiltered,
                        padding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                    ) {
                        PokemonFilterBottomSheetFragment.newInstance(
                            uiState.filteredTypes,
                            uiState.filteredGeneration,
                        ).show(
                            supportFragmentManager,
                            PokemonFilterBottomSheetFragment.TAG,
                        )
                    },
                )
            }
        }
        repeatOnStarted {
            viewModel.navigateToDetailEvent.collect { pokemonId ->
                hideKeyboard()
                startActivity(PokemonDetailActivity.intent(this, pokemonId))
            }
        }
        val fm: FragmentManager = supportFragmentManager
        fm.setFragmentResultListener(RESULT_KEY, this) { key, bundle ->
            val args: PokeFilterUiModel =
                PokemonFilterBottomSheetFragment.argsFrom(bundle)
                    ?: return@setFragmentResultListener
            viewModel.filterPokemon(args)
        }
    }

    private fun initListeners() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }


    companion object {
        val TAG: String = PokemonListActivity::class.java.simpleName
        const val RESULT_KEY = "PokemonListActivity_result_key"
    }
}
