package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.Stat

data class StatUiModel(
    val name: String,
    val amount: Int,
    val limit: Int,
) {
    val progress: Int
        get() = amount * 100 / limit
}

fun Stat.toUi(): StatUiModel =
    StatUiModel(
        name = statNameUi(),
        amount = amount,
        limit = if (name == "total") MAX_PROGRESS_FOR_TOTAL_STAT else MAX_PROGRESS_FOR_SINGLE_STAT,
    )

private fun Stat.statNameUi() =
    when (name) {
        "hp" -> "HP"
        "attack" -> "공격"
        "defense" -> "방어"
        "specialAttack" -> "특수공격"
        "specialDefense" -> "특수방어"
        "speed" -> "스피드"
        "total" -> "총합"
        else -> name
    }

private const val MAX_PROGRESS_FOR_SINGLE_STAT = 300
private const val MAX_PROGRESS_FOR_TOTAL_STAT = 1_000
