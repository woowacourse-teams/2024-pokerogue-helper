package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.NewPokemon
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

data class NewPokemonUiModel(
    val id: String,
    val dexNumber: Long,
    val name: String,
    val imageUrl: String,
    val types: List<TypeUiModel>,
)

fun NewPokemon.toUi(): NewPokemonUiModel =
    NewPokemonUiModel(
        id = id,
        dexNumber = dexNumber,
        name = name,
        imageUrl = imageUrl,
        types = types.toUi(),
    )

fun List<NewPokemon>.toUi(): List<NewPokemonUiModel> = map(NewPokemon::toUi)