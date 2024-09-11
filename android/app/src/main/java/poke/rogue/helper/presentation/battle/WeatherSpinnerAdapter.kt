import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemSpinnerWeatherBinding
import poke.rogue.helper.presentation.battle.model.WeatherUiModel

class WeatherSpinnerAdapter(
    context: Context,
    private val items: MutableList<WeatherUiModel> = mutableListOf(),
) : ArrayAdapter<WeatherUiModel>(context, R.layout.item_spinner_weather, items) {
    private class WeatherViewHolder(val binding: ItemSpinnerWeatherBinding)

    fun updateWeathers(updated: List<WeatherUiModel>) {
        items.clear()
        items.addAll(updated)
        notifyDataSetChanged()
    }

    override fun getCount(): Int = items.size

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
    ): View =
        bindView(convertView, parent, position).apply {
            setBackgroundResource(R.drawable.bg_spinner)
        }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
    ): View = bindView(convertView, parent, position)

    private fun bindView(
        convertView: View?,
        parent: ViewGroup,
        position: Int,
    ): View {
        val viewHolder: WeatherViewHolder
        val view: View

        if (convertView == null) {
            val binding: ItemSpinnerWeatherBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(context),
                    R.layout.item_spinner_weather,
                    parent,
                    false,
                )
            view = binding.root
            viewHolder = WeatherViewHolder(binding)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as WeatherViewHolder
        }
        val weather = getItem(position)
        if (weather != null) {
            viewHolder.binding.weather = weather
        }
        return view
    }
}
