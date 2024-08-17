package poke.rogue.helper.presentation.dex

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.databinding.ActivityPokemonListBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleActivity
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.dex.detail.PokemonDetailActivity
import poke.rogue.helper.presentation.util.activity.hideKeyboard
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import timber.log.Timber

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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initAdapter()
        initObservers()
        binding.root.setOnClickListener {
            hideKeyboard()
        }
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
                hideKeyboard()
                startActivity(PokemonDetailActivity.intent(this, pokemonId))
            }
        }
    }

    companion object {
        val TAG: String = PokemonListActivity::class.java.simpleName
    }
}
