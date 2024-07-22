package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.Stat

data class PokemonDetailUiModel(
    val pokemon: PokemonUiModel,
    val stats: List<StatUiModel>,
    val abilities: List<String>,
    val height: Float,
    val weight: Float,
)

fun PokemonDetail.toUi(): PokemonDetailUiModel =
    PokemonDetailUiModel(
        pokemon = pokemon.toUi(),
        stats = stats.map(Stat::toUi),
        abilities = abilities,
        height = height,
        weight = weight,
    )
