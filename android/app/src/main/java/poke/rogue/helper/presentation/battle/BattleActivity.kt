package poke.rogue.helper.presentation.battle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBattleBinding
import poke.rogue.helper.presentation.base.toolbar.ToolbarActivity
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.WeatherUiModel
import poke.rogue.helper.presentation.battle.model.hasWeatherEffect
import poke.rogue.helper.presentation.battle.selection.BattleSelectionActivity
import poke.rogue.helper.presentation.battle.selection.pokemon.addPokemonTypes
import poke.rogue.helper.presentation.battle.view.itemSelectListener
import poke.rogue.helper.presentation.dex.detail.PokemonDetailActivity
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.context.startActivity
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.parcelable
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.serializable
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.setImage
import poke.rogue.helper.presentation.util.view.setOnSingleClickListener

class BattleActivity : ToolbarActivity<ActivityBattleBinding>(R.layout.activity_battle) {
    private val viewModel by viewModel<BattleViewModel> {
        parametersOf(
            intent.getStringExtra(POKEMON_ID),
            intent.serializable(SELECTION_TYPE),
        )
    }
    private val weatherAdapter by lazy {
        WeatherSpinnerAdapter(this)
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data =
                    result.data?.parcelable<SelectionData>(BattleSelectionActivity.KEY_SELECTION_RESULT)
                if (data != null) {
                    viewModel.updatePokemonSelection(data)
                }
            }
        }
    override val toolbar: Toolbar
        get() = binding.toolbarBattle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initSpinner()
        initObserver()
    }

    private fun initView() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun initSpinner() {
        binding.spinnerWeather.adapter = weatherAdapter
        binding.spinnerWeather.onItemSelectedListener =
            itemSelectListener<WeatherUiModel> {
                viewModel.updateWeather(it)
            }
    }

    private fun initObserver() {
        observeWeathers()
        observeWeatherPosition()
        observeWeatherEffect()
        observeSelectionState()
        observeNavigationEvent()
        observeBattleResult()
    }

    private fun observeWeathers() {
        repeatOnStarted {
            viewModel.weathers.collect {
                weatherAdapter.updateWeathers(it)
            }
        }
    }

    private fun observeWeatherPosition() {
        repeatOnStarted {
            viewModel.weatherPos.collect {
                binding.spinnerWeather.setSelection(it)
            }
        }
    }

    private fun observeWeatherEffect() {
        repeatOnStarted {
            viewModel.showWeatherEffect.collect {
                binding.ivWeatherIcon.isSelected = it
            }
        }
    }

    private fun observeSelectionState() {
        repeatOnStarted {
            viewModel.selectedState.collect {
                if (it.minePokemon is BattleSelectionUiState.Selected) {
                    val selected = it.minePokemon.content
                    binding.ivMinePokemon.setImage(selected.backImageUrl)
                    binding.tvMinePokemon.text = selected.name
                    binding.flexboxMineTypes.addPokemonTypes(
                        types = selected.types,
                        spacingBetweenTypes = 4.dp,
                    )
                    binding.btnMinePokemonDetail.setOnSingleClickListener {
                        viewModel.navigationToDetail(selected.id)
                    }
                }

                if (it.skill is BattleSelectionUiState.Selected) {
                    binding.tvSkillTitle.text = it.skill.content.name
                }

                if (it.opponentPokemon is BattleSelectionUiState.Selected) {
                    val selected = it.opponentPokemon.content
                    binding.ivOpponentPokemon.setImage(selected.frontImageUrl)
                    binding.tvOpponentPokemon.text = selected.name
                    binding.flexboxOpponentTypes.addPokemonTypes(
                        types = selected.types,
                        spacingBetweenTypes = 4.dp,
                    )
                    binding.btnOpponentPokemonDetail.setOnSingleClickListener {
                        viewModel.navigationToDetail(selected.id)
                    }
                }

                if (it.weather is BattleSelectionUiState.Selected) {
                    val selected = it.weather.content
                    binding.ivWeatherIcon.setImage(selected.icon.iconResId)

                    val hasEffect = selected.hasWeatherEffect()
                    binding.ivWeatherIcon.isEnabled = hasEffect
                    if (!hasEffect) viewModel.hideWeatherEffect()
                    binding.tvWeatherEffect.text = selected.effect
                }
            }
        }
    }

    private fun observeNavigationEvent() {
        repeatOnStarted {
            viewModel.navigationEvent.collect { event ->
                when (event) {
                    is NavigateToSelection -> navigateToSelection(event)
                    is NavigateToDetail -> navigateToDetail(event)
                }
            }
        }
    }

    private fun observeBattleResult() {
        repeatOnStarted {
            viewModel.battleResult.collect {
                if (it is BattleResultUiState.Success) {
                    val result = it.result
                    binding.tvPowerContent.text = result.power
                    binding.tvMultiplierContent.text = result.multiplier
                    binding.tvMultiplierContent.setTextColor(colorOf(result.colorRes))
                    binding.tvCalculatedPowerContent.text = result.calculatedResult
                    binding.tvAccuracyContent.text =
                        stringOf(R.string.battle_accuracy_title, result.accuracy)
                }
            }
        }
    }

    private fun navigateToSelection(event: NavigateToSelection) {
        val (selectionMode, previousSelection) = event
        val intent =
            BattleSelectionActivity.intent(
                this@BattleActivity,
                selectionMode,
                previousSelection,
            )
        activityResultLauncher.launch(intent)
    }

    private fun navigateToDetail(event: NavigateToDetail) {
        startActivity<PokemonDetailActivity> {
            putExtras(
                PokemonDetailActivity.intent(
                    this@BattleActivity,
                    event.pokemonId,
                ),
            )
        }
    }

    companion object {
        private const val POKEMON_ID = "pokemonId"
        private const val SELECTION_TYPE = "selectionType"

        fun intent(
            context: Context,
            pokemonId: String,
            isMine: Boolean,
        ): Intent =
            Intent(context, BattleActivity::class.java).apply {
                putExtra(POKEMON_ID, pokemonId)
                val selectionType = if (isMine) SelectionType.MINE else SelectionType.OPPONENT
                putExtra(SELECTION_TYPE, selectionType)
            }
    }
}
