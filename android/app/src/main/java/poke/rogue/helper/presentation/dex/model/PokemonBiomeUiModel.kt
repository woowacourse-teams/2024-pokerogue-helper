package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.PokemonBiome
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

data class PokemonBiomeUiModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val types: List<TypeUiModel>,
)

fun List<PokemonBiome>.toUi(allBiomes: List<Biome>): List<PokemonBiomeUiModel> =
    this.flatMap { pokemonBiome ->
        allBiomes.filter { biome ->
            pokemonBiome.id == biome.id
        }.map { biome ->
            PokemonBiomeUiModel(
                id = pokemonBiome.id,
                name = biome.name,
                imageUrl = biome.image,
                types = biome.pokemonType.toUi(),
            )
        }
    }

fun PokemonBiome.toUi(): PokemonBiomeUiModel =
    PokemonBiomeUiModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        types = pokemonType.toUi(),
    )

fun List<PokemonBiome>.toUi(): List<PokemonBiomeUiModel> = this.map(PokemonBiome::toUi)
