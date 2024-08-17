package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.NewAbility

data class NewAbilityUiModel(
    val id: String,
    val name: String,
    val description: String,
    val passive: Boolean,
    val hidden: Boolean,
)

fun NewAbility.toPokemonDetailUi(): NewAbilityUiModel =
    NewAbilityUiModel(
        id = id,
        name = name,
        description = description,
        passive = passive,
        hidden = hidden,
    )

fun List<NewAbility>.toPokemonDetailUi(): List<NewAbilityUiModel> = map(NewAbility::toPokemonDetailUi)
