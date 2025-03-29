package poke.rogue.helper.presentation.dex.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.children
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityPokemonDetail2Binding
import poke.rogue.helper.presentation.ability.AbilityActivity
import poke.rogue.helper.presentation.base.toolbar.ToolbarActivity
import poke.rogue.helper.presentation.battle.BattleActivity
import poke.rogue.helper.presentation.biome.detail.BiomeDetailActivity
import poke.rogue.helper.presentation.home.HomeActivity
import poke.rogue.helper.presentation.util.context.stringArrayOf
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.context.toast
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.loadImageWithProgress
import poke.rogue.helper.ui.component.PokeChip
import poke.rogue.helper.ui.layout.PaddingValues
import poke.rogue.helper.ui.layout.applyTo

class PokemonDetailActivity2 :
    ToolbarActivity<ActivityPokemonDetail2Binding>(R.layout.activity_pokemon_detail2) {
    private val viewModel by viewModel<PokemonDetailViewModel>()
    override val toolbar: Toolbar?
        get() = null

    private lateinit var pokemonDetailPagerAdapter: PokemonDetailPagerAdapter

    private var isExpanded = false

    private var chipSpecs: List<PokeChip.Spec> = listOf()
    private var iconOnlyChipSpecs: List<PokeChip.Spec> = listOf()
    private var fullNameChipSpecs: List<PokeChip.Spec> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updatePokemonDetail(intent.getStringExtra(POKEMON_ID).toString())

        binding.eventHandler = viewModel
        binding.lifecycleOwner = this
        binding.vm = viewModel

        initAdapter()
        initObservers()
        initFloatingButtonsHandler()
        binding.motionLayout.setTransitionListener(
            object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                ) {
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float,
                ) {
                }

                override fun onTransitionCompleted(
                    motionLayout: MotionLayout?,
                    currentId: Int,
                ) {
                    if (currentId == R.id.end) {
                        updateChipIconsOnly()
                    } else if (currentId == R.id.start) {
                        updateChipWithLabels()
                    }
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float,
                ) {
                }
            },
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(IS_EXPANDED, isExpanded)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        isExpanded = savedInstanceState.getBoolean(IS_EXPANDED)
        super.onRestoreInstanceState(savedInstanceState)
        updateFloatingButtonsState()
    }

    private fun initAdapter() {
        pokemonDetailPagerAdapter = PokemonDetailPagerAdapter(this)
        binding.pagerPokemonDetail.apply {
            adapter = pokemonDetailPagerAdapter
        }

        val tabTitles = stringArrayOf(R.array.pokemon_detail_tab_titles)
        TabLayoutMediator(
            binding.tabLayoutPokemonDetail,
            binding.pagerPokemonDetail,
        ) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun initObservers() {
        observePokemonDetailUi()
        observeNavigationEvent()
    }

    private fun initFloatingButtonsHandler() {
        binding.fabPokemonDetailBattle.setOnClickListener {
            toggleFloatingButtons()
        }
    }

    private fun observePokemonDetailUi() {
        repeatOnStarted {
            viewModel.uiState.collect { pokemonDetail ->
                when (pokemonDetail) {
                    is PokemonDetailUiState.IsLoading -> return@collect
                    is PokemonDetailUiState.Success -> {
                        bindPokemonDetail(pokemonDetail)
                    }
                }
            }
        }
    }

    private fun observeNavigationEvent() {
        repeatOnStarted {
            viewModel.navigationEvent.collect { event ->
                when (event) {
                    is PokemonDetailViewModel.NavigationEvent.ToPokemonList ->
                        onBackPressedDispatcher.onBackPressed()

                    is PokemonDetailViewModel.NavigationEvent.ToAbilityDetail ->
                        startActivity(
                            AbilityActivity.intent(this, event.id),
                        )

                    is PokemonDetailViewModel.NavigationEvent.ToBiomeDetail ->
                        startActivity(
                            BiomeDetailActivity.intent(this, event.id),
                        )

                    is PokemonDetailViewModel.NavigationEvent.ToHome ->
                        startActivity(
                            HomeActivity.intent(this),
                        )

                    is PokemonDetailViewModel.NavigationEvent.ToBattle ->
                        startActivity(
                            battleIntent(
                                event,
                            ),
                        )

                    is PokemonDetailViewModel.NavigationEvent.ToPokemonDetail ->
                        navigateToPokemonDetail(
                            event,
                        )

                    is PokemonDetailViewModel.NavigationEvent.None -> return@collect
                }
            }
        }
    }

    private fun bindPokemonDetail(pokemonDetail: PokemonDetailUiState.Success) {
        with(binding) {
            ivPokemonDetailPokemon.loadImageWithProgress(
                pokemonDetail.pokemon.imageUrl,
                progressIndicatorPokemonDetail,
            )

            tvPokemonDetailPokemonName.text =
                stringOf(
                    R.string.pokemon_list_poke_name_format,
                    pokemonDetail.pokemon.name,
                    pokemonDetail.pokemon.dexNumber,
                )
            val nowChipSpecs =
                pokemonDetail.pokemon.types.map {
                    PokeChip.Spec(
                        id = it.id,
                        label = stringOf(it.typeName),
                        leadingIconRes = it.typeIconResId,
                        colors =
                            PokeChip.Colors(
                                selectedContainerColor = it.typeColor,
                                containerColor = R.color.poke_black,
                            ),
                        sizes =
                            PokeChip.Sizes(
                                leadingIconSize = 20.dp,
                            ),
                        strokeWidth = 0.dp,
                    )
                }
            chipSpecs = nowChipSpecs
            fullNameChipSpecs = nowChipSpecs

            chipGroupPokemonDetailTypes.submitList(
                nowChipSpecs,
            )
        }
    }

    private fun battleIntent(battleEvent: PokemonDetailViewModel.NavigationEvent.ToBattle): Intent =
        when (battleEvent) {
            is PokemonDetailViewModel.NavigationEvent.ToBattle.WithMyPokemon ->
                BattleActivity.intent(
                    this@PokemonDetailActivity2,
                    pokemonId = battleEvent.pokemon.id,
                    isMine = true,
                )

            is PokemonDetailViewModel.NavigationEvent.ToBattle.WithOpponentPokemon ->
                BattleActivity.intent(
                    this@PokemonDetailActivity2,
                    pokemonId = battleEvent.pokemon.id,
                    isMine = false,
                )
        }

    private fun navigateToPokemonDetail(event: PokemonDetailViewModel.NavigationEvent.ToPokemonDetail) {
        when (event) {
            is PokemonDetailViewModel.NavigationEvent.ToPokemonDetail.Success ->
                startActivity(
                    intent(this, event.pokemonId),
                )

            is PokemonDetailViewModel.NavigationEvent.ToPokemonDetail.Failure ->
                toast(
                    this.stringOf(
                        R.string.pokemon_detail_evolution_same_with_current_pokemon_message,
                        event.pokemonName,
                    ),
                )
        }
    }

    private fun toggleFloatingButtons() {
        val rotateOpen: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate_open)
        val rotateClose: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate_close)
        val fromBottom: Animation = AnimationUtils.loadAnimation(this, R.anim.from_bottom)
        val toBottom: Animation = AnimationUtils.loadAnimation(this, R.anim.to_bottom)

        updateFloatingButtonsState()
        with(binding) {
            if (!isExpanded) {
                fabPokemonDetailBattle.startAnimation(rotateOpen)
                efabPokemonDetailBattleWithMine.startAnimation(fromBottom)
                efabPokemonDetailBattleWithOpponent.startAnimation(fromBottom)
            } else {
                fabPokemonDetailBattle.startAnimation(rotateClose)
                efabPokemonDetailBattleWithMine.startAnimation(toBottom)
                efabPokemonDetailBattleWithOpponent.startAnimation(toBottom)
            }
        }

        isExpanded = !isExpanded
    }

    private fun updateFloatingButtonsState() {
        with(binding) {
            if (isExpanded) {
                efabPokemonDetailBattleWithMine.visibility = View.VISIBLE
                efabPokemonDetailBattleWithOpponent.visibility = View.VISIBLE
            } else {
                efabPokemonDetailBattleWithMine.visibility = View.INVISIBLE
                efabPokemonDetailBattleWithOpponent.visibility = View.INVISIBLE
            }
        }
    }

    private fun updateChipIconsOnly() {
        iconOnlyChipSpecs =
            chipSpecs.map { spec ->
                spec.copy(
                    label = "",
                    sizes =
                        PokeChip.Sizes(
                            leadingIconSize = 20.dp,
                            leadingSpacing = 0.dp,
                            trailingSpacing = 0.dp,
                        ),
                )
            }

        val zeroPadding = PaddingValues(all = 4.dp)

        binding.chipGroupPokemonDetailTypes.children.forEach { chip ->
            zeroPadding.applyTo(chip)
        }

        chipSpecs = iconOnlyChipSpecs
        binding.chipGroupPokemonDetailTypes.submitList(chipSpecs)
    }

    private fun updateChipWithLabels() {
        chipSpecs = fullNameChipSpecs
        binding.chipGroupPokemonDetailTypes.submitList(chipSpecs)
    }

    companion object {
        private const val POKEMON_ID = "pokemonId"
        private const val IS_EXPANDED = "isExpanded"

        fun intent(
            context: Context,
            pokemonId: String,
        ): Intent =
            Intent(context, PokemonDetailActivity2::class.java).apply {
                putExtra(POKEMON_ID, pokemonId)
            }
    }
}
