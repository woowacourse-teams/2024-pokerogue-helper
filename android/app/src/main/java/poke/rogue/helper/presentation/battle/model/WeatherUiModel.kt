package poke.rogue.helper.presentation.battle.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.R
import poke.rogue.helper.data.model.Weather

@Parcelize
data class WeatherUiModel(
    val id: String,
    val icon: WeatherIcon,
    val description: String,
    val effect: String,
) : Parcelable

enum class WeatherIcon(
    @DrawableRes val iconResId: Int,
) {
    NONE(R.drawable.icon_close),
    CLEAR(R.drawable.icon_sun),
    RAIN(R.drawable.icon_rain),
    SANDSTORM(R.drawable.icon_air),
    HAIL(R.drawable.icon_hail),
    SNOW(R.drawable.icon_snow),
    FOG(R.drawable.icon_foggy),
    HEAVY_RAIN(R.drawable.icon_rain),
    STRONG_SUN(R.drawable.icon_sun),
    TURBULENCE(R.drawable.icon_air),
}

fun WeatherUiModel.hasWeatherEffect(): Boolean = effect.isNotBlank()

fun Weather.toUi(): WeatherUiModel {
    val weatherIcon =
        when (id) {
            "sunny" -> WeatherIcon.CLEAR
            "rain" -> WeatherIcon.RAIN
            "snow" -> WeatherIcon.SNOW
            "sandstorm" -> WeatherIcon.SANDSTORM
            "hail" -> WeatherIcon.HAIL
            "fog" -> WeatherIcon.FOG
            "heavy_rain" -> WeatherIcon.HEAVY_RAIN
            "harsh_sun" -> WeatherIcon.STRONG_SUN
            "strong_winds" -> WeatherIcon.TURBULENCE
            else -> WeatherIcon.NONE
        }

    val effectString = if (id == "none") "" else effects.joinToString("\n")
    val descriptionString = if (id == "none") "날씨가 없다" else description

    return WeatherUiModel(
        id = id,
        icon = weatherIcon,
        description = descriptionString,
        effect = effectString,
    )
}
