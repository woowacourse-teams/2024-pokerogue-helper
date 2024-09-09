package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.PokemonBiome
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

data class PokemonBiomeUiModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val types: List<TypeUiModel>,
)

fun PokemonBiome.toUi(): PokemonBiomeUiModel =
    PokemonBiomeUiModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        types = pokemonType.toUi(),
    )

fun List<PokemonBiome>.toUi(): List<PokemonBiomeUiModel> = this.map(PokemonBiome::toUi)
