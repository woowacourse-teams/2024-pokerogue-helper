package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.Type

data class PokemonUiModel(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val types: List<Type>,
)

fun Pokemon.toUi(): PokemonUiModel =
    PokemonUiModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        types = types,
    )
