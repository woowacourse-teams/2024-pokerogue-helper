package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.MoveCategory
import poke.rogue.helper.data.model.PokemonMove
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

data class PokemonMovesUiModel(
    val id: Long,
    val name: String,
    val level: Int,
    val power: Int,
    val type: TypeUiModel,
    val accuracy: Int,
    val category: MoveCategory,
)

fun PokemonMove.toUi(): PokemonMovesUiModel =
    PokemonMovesUiModel(
        id = id,
        name = name,
        level = level,
        power = power,
        type = type.toUi(),
        accuracy = accuracy,
        category = category,
    )
