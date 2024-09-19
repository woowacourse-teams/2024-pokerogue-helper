package poke.rogue.helper.presentation.battle

import WeatherSpinnerAdapter
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultBattleRepository
import poke.rogue.helper.databinding.ActivityBattleBinding
import poke.rogue.helper.presentation.base.toolbar.ToolbarActivity
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.WeatherUiModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionActivity
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.parcelable
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.setImage

class BattleActivity : ToolbarActivity<ActivityBattleBinding>(R.layout.activity_battle) {
    private val viewModel by viewModels<BattleViewModel> {
        BattleViewModel.factory(DefaultBattleRepository.instance(this))
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
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedWeather = parent.getItemAtPosition(position) as WeatherUiModel
                    viewModel.updateWeather(selectedWeather)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun initObserver() {
        repeatOnStarted {
            viewModel.weathers.collect {
                weatherAdapter.updateWeathers(it)
            }
        }

        repeatOnStarted {
            viewModel.selectedState.collect {
                if (it.minePokemon is BattleSelectionUiState.Selected) {
                    val selected = it.minePokemon.content
                    binding.ivMinePokemon.setImage(selected.backImageUrl)
                    binding.tvMinePokemon.text = selected.name
                }

                if (it.skill is BattleSelectionUiState.Selected) {
                    binding.tvSkillTitle.text = it.skill.content.name
                }

                if (it.opponentPokemon is BattleSelectionUiState.Selected) {
                    val selected = it.opponentPokemon.content
                    binding.ivOpponentPokemon.setImage(selected.frontImageUrl)
                    binding.tvOpponentPokemon.text = selected.name
                }

                if (it.weather is BattleSelectionUiState.Selected) {
                    val selected = it.weather.content
                    binding.tvWeatherDescription.text = selected.effect
                }
            }
        }

        repeatOnStarted {
            viewModel.navigateToSelection.collect { (selectionMode, previousSelection) ->
                val intent =
                    BattleSelectionActivity.intent(
                        this@BattleActivity,
                        selectionMode,
                        previousSelection,
                    )
                activityResultLauncher.launch(intent)
            }
        }

        repeatOnStarted {
            viewModel.battleResult.collect {
                if (it is BattleResultUiState.Success) {
                    val result = it.result
                    binding.tvPowerContent.text = result.power
                    binding.tvMultiplierContent.text = result.multiplier
                    binding.tvMultiplierContent.setTextColor(colorOf(result.colorRes))
                    binding.tvCalculatedPowerContent.text = result.calculatedResult
                    binding.tvAccuracyContent.text =
                        getString(R.string.battle_accuracy_title, result.accuracy)
                }
            }
        }
    }
}
