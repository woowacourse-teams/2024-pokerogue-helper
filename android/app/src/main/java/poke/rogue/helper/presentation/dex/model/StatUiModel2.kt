package poke.rogue.helper.presentation.dex.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import poke.rogue.helper.R
import poke.rogue.helper.data.model.Stat

data class StatUiModel(
    @StringRes val name: Int,
    val amount: Int,
    val limit: Int,
    @ColorRes val color: Int = 0,
) {
    val progress: Int
        get() = amount * 100 / limit
}

fun Stat.toUi(): StatUiModel =
    when (name) {
        "hp" ->
            StatUiModel(
                name = R.string.pokemon_stat_hp,
                amount = amount,
                limit = MAX_HP_LIMIT,
                color = R.color.stat_hp,
            )

        "attack" ->
            StatUiModel(
                name = R.string.pokemon_stat_attack,
                amount = amount,
                limit = MAX_ATTACK_LIMIT,
                color = R.color.stat_attack,
            )

        "defense" ->
            StatUiModel(
                name = R.string.pokemon_stat_defense,
                amount = amount,
                limit = MAX_DEFENSE_LIMIT,
                color = R.color.stat_defense,
            )

        "specialAttack" ->
            StatUiModel(
                name = R.string.pokemon_stat_special_attack,
                amount = amount,
                limit = MAX_SPECIAL_ATTACK_LIMIT,
                color = R.color.stat_special_attack,
            )

        "specialDefense" ->
            StatUiModel(
                name = R.string.pokemon_stat_special_defense,
                amount = amount,
                limit = MAX_SPECIAL_DEFENSE_LIMIT,
                color = R.color.stat_special_defense,
            )

        "speed" ->
            StatUiModel(
                name = R.string.pokemon_stat_speed,
                amount = amount,
                limit = MAX_SPEED_LIMIT,
                color = R.color.stat_speed,
            )

        "total" ->
            StatUiModel(
                name = R.string.pokemon_stat_base_stat,
                amount = amount,
                limit = MAX_TOTAL_LIMIT,
                color = R.color.stat_total,
            )

        else -> error("Unknown stat name: $name")
    }

private const val MAX_HP_LIMIT = 255
private const val MAX_ATTACK_LIMIT = 190
private const val MAX_DEFENSE_LIMIT = 250
private const val MAX_SPECIAL_ATTACK_LIMIT = 194
private const val MAX_SPECIAL_DEFENSE_LIMIT = 250
private const val MAX_SPEED_LIMIT = 200
private const val MAX_TOTAL_LIMIT = 800
