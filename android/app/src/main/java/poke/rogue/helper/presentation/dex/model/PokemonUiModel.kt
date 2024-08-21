package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

data class PokemonUiModel(
    val id: String = "",
    val dexNumber: Long = 0,
    val name: String,
    val imageUrl: String,
    val types: List<TypeUiModel>,
)

fun Pokemon.toUi(): PokemonUiModel =
    PokemonUiModel(
        id = id,
        dexNumber = dexNumber,
        name = name,
        imageUrl = imageUrl,
        types = types.toUi(),
    )

fun List<Pokemon>.toUi(): List<PokemonUiModel> = map(Pokemon::toUi)
