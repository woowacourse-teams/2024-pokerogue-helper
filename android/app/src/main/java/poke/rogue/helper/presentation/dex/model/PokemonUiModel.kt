package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.presentation.type.model.TypeUiModel

data class PokemonUiModel(
    val id: Long,
    val dexNumber: Long,
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
        types = types.mapNotNull(TypeUiModel::fromType),
    )
