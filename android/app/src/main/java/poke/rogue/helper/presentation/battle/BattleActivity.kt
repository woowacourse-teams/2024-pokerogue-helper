package poke.rogue.helper.presentation.battle

import WeatherSpinnerAdapter
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBattleBinding
import poke.rogue.helper.presentation.battle.model.WeatherUiModel
import poke.rogue.helper.presentation.toolbar.ToolbarActivity

class BattleActivity : ToolbarActivity<ActivityBattleBinding>(R.layout.activity_battle) {
    private val viewModel by viewModels<BattleViewModel>()
    override val toolbar: Toolbar
        get() = binding.toolbarBattle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initSpinner()
    }

    private fun initView() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun initSpinner() {
        val weatherAdapter = WeatherSpinnerAdapter(this, WeatherUiModel.DUMMY)
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
}
