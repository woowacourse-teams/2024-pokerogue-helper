package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.PokemonDetailAbility

data class PokemonDetailUiModel(
    val id: String,
    val name: String,
    val description: String,
    val passive: Boolean,
    val hidden: Boolean,
)

fun PokemonDetailAbility.toPokemonDetailUi(): PokemonDetailUiModel =
    PokemonDetailUiModel(
        id = id,
        name = name,
        description = description,
        passive = passive,
        hidden = hidden,
    )

fun List<PokemonDetailAbility>.toPokemonDetailUi(): List<PokemonDetailUiModel> = map(PokemonDetailAbility::toPokemonDetailUi)
