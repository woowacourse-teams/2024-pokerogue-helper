package poke.rogue.helper.presentation.dex

import android.content.res.Configuration
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityPokemonListBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleActivity
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.dex.detail.PokemonDetailActivity2
import poke.rogue.helper.presentation.dex.filter.PokeFilterUiModel
import poke.rogue.helper.presentation.dex.filter.PokemonFilterBottomSheetFragment
import poke.rogue.helper.presentation.dex.sort.PokemonSortBottomSheetFragment
import poke.rogue.helper.presentation.dex.sort.PokemonSortUiModel
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
    private val viewModel by viewModel<PokemonListViewModel>()
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
            addOnScrollListener(pokemonListScrollListener())
        }
    }

    private fun pokemonListScrollListener() =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int,
            ) {
                if (!recyclerView.canScrollVertically(-1)) {
                    hideScrollUpButton()
                } else {
                    showScrollUpButton()
                }
            }

            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int,
            ) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    hideScrollUpButton()
                }
            }
        }

    private fun showScrollUpButton() {
        val scrollUpButton = binding.btnPokeListScrollUp
        if (scrollUpButton.isVisible) return
        scrollUpButton.isVisible = true
        scrollUpButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
    }

    private fun hideScrollUpButton() {
        val scrollUpButton = binding.btnPokeListScrollUp
        if (scrollUpButton.isGone) return
        lifecycleScope.launch {
            delay(300L)
            scrollUpButton.isGone = true
            scrollUpButton.startAnimation(
                AnimationUtils.loadAnimation(
                    this@PokemonListActivity,
                    R.anim.fade_out,
                ),
            )
        }
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                pokemonAdapter.submitList(uiState.pokemons) {
                    binding.rvPokemonList.scrollToPosition(0)
                }

                binding.chipPokeFiter.bindPokeChip(
                    PokeChip.Spec(
                        label =
                            stringOf(
                                R.string.dex_filter_chip,
                                if (uiState.isFiltered) uiState.filterCount.toString() else "",
                            ),
                        trailingIconRes = R.drawable.ic_filter,
                        isSelected = uiState.isFiltered,
                        padding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                        onSelect = {
                            PokemonFilterBottomSheetFragment.newInstance(
                                uiState.filteredTypes,
                                uiState.filteredGeneration,
                            ).show(
                                supportFragmentManager,
                                PokemonFilterBottomSheetFragment.TAG,
                            )
                        },
                    ),
                )

                binding.chipPokeSort.bindPokeChip(
                    PokeChip.Spec(
                        label = this.stringOf(uiState.sort.label),
                        trailingIconRes = R.drawable.ic_sort,
                        isSelected = uiState.isSorted,
                        padding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                        onSelect = {
                            PokemonSortBottomSheetFragment.newInstance(uiState.sort).show(
                                supportFragmentManager,
                                PokemonSortBottomSheetFragment.TAG,
                            )
                        },
                    ),
                )
            }
        }
        repeatOnStarted {
            viewModel.navigateToDetailEvent.collect { pokemonId ->
                hideKeyboard()
                startActivity(PokemonDetailActivity2.intent(this, pokemonId))
            }
        }

        supportFragmentManager.setFragmentResultListener(FILTER_RESULT_KEY, this) { key, bundle ->
            val filterArgs: PokeFilterUiModel =
                PokemonFilterBottomSheetFragment.argsFrom(bundle)
                    ?: return@setFragmentResultListener
            viewModel.filterPokemon(filterArgs)
        }
        supportFragmentManager.setFragmentResultListener(SORT_RESULT_KEY, this) { key, bundle ->
            val sortArgs: PokemonSortUiModel =
                PokemonSortBottomSheetFragment.argsFrom(bundle)
                    ?: return@setFragmentResultListener
            viewModel.sortPokemon(sortArgs)
        }
    }

    private fun initListeners() {
        binding.btnPokeListScrollUp.setOnClickListener {
            binding.rvPokemonList.scrollToPosition(0)
            binding.appBarPokemonList.setExpanded(true, true)
        }
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun String.clean() =
        this
            .replace("\\s".toRegex(), "")
            .replace("[^a-zA-Z0-9ㄱ-ㅎ가-힣]".toRegex(), "")

    companion object {
        const val FILTER_RESULT_KEY = "FILTER_RESULT_KEY_result_key"
        const val SORT_RESULT_KEY = "SORT_RESULT_KEY_result_key"
    }
}
