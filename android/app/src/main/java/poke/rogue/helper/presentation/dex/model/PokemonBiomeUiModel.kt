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

fun List<PokemonBiome>.toUi(allBiomes: List<Biome>): List<PokemonBiomeUiModel> {
    val list = mutableListOf<PokemonBiomeUiModel>()

    this.map { pokemonBiome ->
        allBiomes.forEach { biome ->
            if (pokemonBiome.id == biome.id) {
                list.add(
                    PokemonBiomeUiModel(
                        id = pokemonBiome.id,
                        name = biome.name,
                        imageUrl = biome.image,
                        types = biome.pokemonType.toUi(),
                    ),
                )
            }
        }
    }
    return list.toList()
}
