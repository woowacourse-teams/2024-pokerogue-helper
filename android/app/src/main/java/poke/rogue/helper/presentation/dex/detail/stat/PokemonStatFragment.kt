package poke.rogue.helper.presentation.dex.detail.stat

import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.fragment.app.activityViewModels
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonStatBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.detail.PokemonDetailUiState
import poke.rogue.helper.presentation.dex.detail.PokemonDetailViewModel
import poke.rogue.helper.presentation.dex.model.AbilityTitleUiModel
import poke.rogue.helper.presentation.dex.model.NewAbilityUiModel
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class PokemonStatFragment : BindingFragment<FragmentPokemonStatBinding>(R.layout.fragment_pokemon_stat) {
    private val activityViewModel: PokemonDetailViewModel by activityViewModels()

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
                    is PokemonDetailUiState.Success -> bindDatas(uiState)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
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

    private fun bindDatas(uiState: PokemonDetailUiState.Success) {
        binding.apply {
            pokemonStatAdapter.submitList(uiState.stats)
            abilityAdapter.submitList(uiState.abilities.toUi())
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


private fun NewAbilityUiModel.toUi(): AbilityTitleUiModel =
    AbilityTitleUiModel(
        id = id,
        name = name,
    )

private fun List<NewAbilityUiModel>.toUi(): List<AbilityTitleUiModel> = map(NewAbilityUiModel::toUi)