package poke.rogue.helper.presentation.battle

import WeatherSpinnerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultBattleRepository
import poke.rogue.helper.databinding.ActivityBattleBinding
import poke.rogue.helper.presentation.base.toolbar.ToolbarActivity
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.WeatherUiModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionActivity
import poke.rogue.helper.presentation.util.parcelable
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.setCroppedImage

class BattleActivity : ToolbarActivity<ActivityBattleBinding>(R.layout.activity_battle) {
    private val viewModel by viewModels<BattleViewModel> {
        BattleViewModel.factory(DefaultBattleRepository.instance())
    }
    private val weatherAdapter by lazy {
        WeatherSpinnerAdapter(this)
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
                    val selected = it.minePokemon.selected
                    binding.ivMinePokemon.setCroppedImage(selected.backImageUrl)
                    binding.tvMinePokemon.text = selected.name
                }

                if (it.skill is BattleSelectionUiState.Selected) {
                    binding.tvSkillTitle.text = it.skill.selected.name
                }

                if (it.opponentPokemon is BattleSelectionUiState.Selected) {
                    val selected = it.opponentPokemon.selected
                    binding.ivOpponentPokemon.setCroppedImage(selected.frontImageUrl)
                    binding.tvOpponentPokemon.text = selected.name
                }

                if (it.weather is BattleSelectionUiState.Selected) {
                    val selected = it.weather.selected
                    binding.tvWeatherDescription.text = selected.effect
                }
            }
        }

        repeatOnStarted {
            viewModel.navigateToSelection.collect { previousSelection ->
                val intent =
                    BattleSelectionActivity.intent(
                        this@BattleActivity,
                        previousSelection,
                    )
                startActivityForResult(intent, REQUEST_CODE)
            }
        }

        repeatOnStarted {
            viewModel.battleResult.collect {
                if (it is BattleResultUiState.Success) {
                    val result = it.result
                    binding.tvPowerContent.text = result.power
                    binding.tvMultiplierContent.text = result.multiplier
                    binding.tvCalculatedPowerContent.text = result.calculatedResult
                    binding.tvAccuracyContent.text = getString(R.string.battle_accuracy_title, result.accuracy)
                }
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != REQUEST_CODE) return
        if (resultCode == RESULT_OK) {
            val result =
                data?.parcelable<SelectionData>(BattleSelectionActivity.KEY_SELECTION_RESULT)
            if (result != null) {
                viewModel.updatePokemonSelection(result)
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 1
    }
}
