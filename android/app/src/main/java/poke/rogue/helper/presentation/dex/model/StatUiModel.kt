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
        name = name,
        amount = amount,
        limit = 100,
    )
