package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.PokemonMove
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

data class PokemonMoveUiModel(
    val id: Long,
    val name: String,
    val level: Int,
    val power: String,
    val type: TypeUiModel,
    val accuracy: Int,
    val category: MoveCategoryUiModel,
) {
    companion object {
        const val NO_POWER = "-"
    }
}

fun PokemonMove.toUi(): PokemonMoveUiModel =
    PokemonMoveUiModel(
        id = id,
        name = name,
        level = level,
        power = if (power == PokemonMove.NO_POWER_VALUE) PokemonMoveUiModel.NO_POWER else power.toString(),
        type = type.toUi(),
        accuracy = accuracy,
        category = category.toUi(),
    )

fun List<PokemonMove>.toUi(): List<PokemonMoveUiModel> = map(PokemonMove::toUi)
