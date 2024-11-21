package poke.rogue.helper.presentation.biome.model

import poke.rogue.helper.data.model.biome.BiomePokemon
import poke.rogue.helper.data.model.biome.BossPokemon
import poke.rogue.helper.data.model.biome.GymPokemon
import poke.rogue.helper.data.model.biome.WildPokemon
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel1
import poke.rogue.helper.presentation.type.model.toUi

data class BiomePokemonUiModel(
    val grade: String,
    val gymLeaderUrl: String?,
    val type: TypeUiModel1?,
    val pokemons: List<PokemonUiModel>,
)

fun WildPokemon.toUi(): BiomePokemonUiModel =
    BiomePokemonUiModel(
        grade = tier,
        gymLeaderUrl = null,
        type = null,
        pokemons =
            pokemons.mapIndexed { index, biomePokemon ->
                biomePokemon.toPokemonUiModel(index)
            },
    )

fun BossPokemon.toUi(): BiomePokemonUiModel =
    BiomePokemonUiModel(
        grade = tier,
        gymLeaderUrl = null,
        type = null,
        pokemons =
            pokemons.mapIndexed { index, biomePokemon ->
                biomePokemon.toPokemonUiModel(index)
            },
    )

fun GymPokemon.toUi(): BiomePokemonUiModel =
    BiomePokemonUiModel(
        grade = gymLeaderName,
        gymLeaderUrl = gymLeaderImage,
        type = gymLeaderTypeLogos.firstOrNull()?.toUi(),
        pokemons =
            pokemons.mapIndexed { index, biomePokemon ->
                biomePokemon.toPokemonUiModel(index)
            },
    )

fun BiomePokemon.toPokemonUiModel(hashId: Int): PokemonUiModel =
    PokemonUiModel(
        id = id,
        hashId = hashId.toLong(),
        dexNumber = 0,
        name = name,
        imageUrl = imageUrl,
        types = types.map { it.toUi() },
    )
