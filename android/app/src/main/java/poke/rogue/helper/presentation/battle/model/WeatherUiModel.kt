package poke.rogue.helper.presentation.battle.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.R

@Parcelize
data class WeatherUiModel(
    val icon: WeatherIcon,
    val description: String,
    val effect: String,
) : Parcelable {
    companion object {
        val DEFAULT_SELECTED = WeatherUiModel(WeatherIcon.NONE, "날씨가 없다", "")

        val DUMMY =
            listOf<WeatherUiModel>(
                WeatherUiModel(WeatherIcon.NONE, "날씨가 없다", ""),
                WeatherUiModel(WeatherIcon.CLEAR, "햇살이 강하다", "불꽃 타입 기술의 위력이 1.5배가 된다"),
                WeatherUiModel(WeatherIcon.RAIN, "비가 계속 내리고 있다", "물 타입 기술의 위력이 1.5배가 된다"),
                WeatherUiModel(WeatherIcon.SNOW, "눈이 계속 내리고 있다", "얼음 타입 포켓몬의 방어가 1.5배 올라간다"),
            )
    }
}

enum class WeatherIcon(
    @DrawableRes val iconResId: Int,
) {
    NONE(R.drawable.icon_close),
    CLEAR(R.drawable.icon_sun),
    RAIN(R.drawable.icon_rain),
    SANDSTORM(R.drawable.icon_sun),
    HAIL(R.drawable.icon_hail),
    SNOW(R.drawable.icon_snow),
    FOG(R.drawable.icon_foggy),
    HEAVY_RAIN(R.drawable.icon_rain),
    STRONG_SUN(R.drawable.icon_sun),
    TURBULENCE(R.drawable.icon_air),
}
