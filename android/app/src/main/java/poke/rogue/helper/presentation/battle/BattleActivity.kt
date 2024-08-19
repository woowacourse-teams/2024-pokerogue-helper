package poke.rogue.helper.presentation.battle

import WeatherSpinnerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBattleBinding
import poke.rogue.helper.presentation.base.toolbar.ToolbarActivity
import poke.rogue.helper.presentation.battle.model.WeatherUiModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionActivity
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.setImage

class BattleActivity : ToolbarActivity<ActivityBattleBinding>(R.layout.activity_battle) {
    private val viewModel by viewModels<BattleViewModel>()
    private val weatherAdapter by lazy {
        WeatherSpinnerAdapter(this)
    }
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override val toolbar: Toolbar
        get() = binding.toolbarBattle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initResultLauncher()
        initSpinner()
        initObserver()
    }

    private fun initView() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun initResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                }
            }
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
                    binding.ivMinePokemon.setImage(selected.backImageUrl)
                    binding.tvMinePokemon.text = selected.name
                }

                if (it.skill is BattleSelectionUiState.Selected) {
                    binding.tvSkillTitle.text = it.skill.selected.name
                }

                if (it.opponentPokemon is BattleSelectionUiState.Selected) {
                    val selected = it.opponentPokemon.selected
                    binding.ivOpponentPokemon.setImage(selected.backImageUrl)
                    binding.tvOpponentPokemon.text = selected.name
                }
            }
        }

        repeatOnStarted {
            viewModel.navigateToSelection.collect { hasSkillSelection ->
                val intent =
                    BattleSelectionActivity.intent(
                        this@BattleActivity,
                        hasSkillSelection,
                    )
                resultLauncher.launch(intent)
            }
        }
    }
}
