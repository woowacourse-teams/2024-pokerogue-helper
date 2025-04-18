package poke.rogue.helper.presentation.dex.detail.stat

import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonStatBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.detail.PokemonDetailUiState
import poke.rogue.helper.presentation.dex.detail.PokemonDetailViewModel
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonStatFragment : BindingFragment<FragmentPokemonStatBinding>(R.layout.fragment_pokemon_stat) {
    private val activityViewModel: PokemonDetailViewModel by activityViewModel()

    private val abilityAdapter by lazy { AbilityTitleAdapter(activityViewModel) }
    private val pokemonStatAdapter by lazy { PokemonStatAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.eventHandler = activityViewModel

        initAdapter()
        repeatOnStarted {
            activityViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is PokemonDetailUiState.IsLoading -> return@collect
                    is PokemonDetailUiState.Success -> bindData(uiState)
                }
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            rvPokemonAbilities.adapter = abilityAdapter
            rvPokemonStats.adapter = pokemonStatAdapter

            rvPokemonAbilities.addItemDecoration(
                LinearSpacingItemDecoration(
                    spacing = 7.dp,
                    includeEdge = false,
                    orientation = LinearSpacingItemDecoration.Orientation.HORIZONTAL,
                ),
            )
        }
    }

    private fun bindData(uiState: PokemonDetailUiState.Success) {
        binding.apply {
            pokemonStatAdapter.submitList(uiState.stats)
            abilityAdapter.submitList(uiState.abilities)
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("progressColor")
        fun ProgressBar.setProgressDrawable(color: Int) {
            val background =
                GradientDrawable().apply {
                    setColor(context.colorOf(R.color.poke_grey_20))
                    cornerRadius = resources.getDimension(R.dimen.progress_bar_corner_radius)
                }

            val progress =
                GradientDrawable().apply {
                    setColor(context.colorOf(color))
                    cornerRadius = resources.getDimension(R.dimen.progress_bar_corner_radius)
                }

            val clipDrawable = ClipDrawable(progress, Gravity.START, ClipDrawable.HORIZONTAL)

            val layerDrawable =
                LayerDrawable(arrayOf(background, clipDrawable)).apply {
                    setId(0, android.R.id.background)
                    setId(1, android.R.id.progress)
                }

            progressDrawable = layerDrawable
        }
    }
}
