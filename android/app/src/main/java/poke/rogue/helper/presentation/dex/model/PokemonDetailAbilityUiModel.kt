package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.PokemonDetailAbility

data class PokemonDetailAbilityUiModel(
    val id: String,
    val name: String,
    val passive: Boolean,
    val hidden: Boolean,
)

fun PokemonDetailAbility.toPokemonDetailUi(): PokemonDetailAbilityUiModel =
    PokemonDetailAbilityUiModel(
        id = id,
        name = name,
        passive = passive,
        hidden = hidden,
    )

fun List<PokemonDetailAbility>.toPokemonDetailUi(): List<PokemonDetailAbilityUiModel> = map(PokemonDetailAbility::toPokemonDetailUi)
