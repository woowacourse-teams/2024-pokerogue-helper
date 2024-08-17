package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.Ability

data class AbilityTitleUiModel(
    val id: String,
    val name: String,
)

fun Ability.toPokemonDetailUi(): AbilityTitleUiModel =
    AbilityTitleUiModel(
        id = id,
        name = title,
    )
