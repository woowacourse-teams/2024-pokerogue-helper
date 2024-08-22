package poke.rogue.helper.data.model

import poke.rogue.helper.local.entity.PokemonEntity
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse2
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class Pokemon(
    val id: String,
    val dexNumber: Long,
    val name: String,
    val formName: String = "",
    val imageUrl: String,
    val backImageUrl: String,
    val types: List<Type>,
    val generation: PokemonGeneration = PokemonGeneration.ONE,
    val baseStat: Int = 0,
    val hp: Int = 0,
    val attack: Int = 0,
    val defense: Int = 0,
    val specialAttack: Int = 0,
    val specialDefense: Int = 0,
    val speed: Int = 0,
) {
    companion object {
        private const val DUMMY_POKEMON_NAME = "이상해씨"
        private const val DUMMY_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        private val DUMMY_TYPES = listOf(Type.GRASS, Type.POISON)
        private const val DUMMY_BACK_IMAGE_URL = ""

        val DUMMY =
            Pokemon(
                id = "1",
                dexNumber = 1,
                name = DUMMY_POKEMON_NAME,
                imageUrl = DUMMY_IMAGE_URL,
                backImageUrl = DUMMY_BACK_IMAGE_URL,
                types = DUMMY_TYPES,
            )
    }
}

fun PokemonResponse.toData(): Pokemon =
    Pokemon(
        id = id.toString(),
        dexNumber = pokedexNumber,
        name = name,
        imageUrl = image,
        backImageUrl = "",
        types = types.map(PokemonTypeResponse::toData),
    )

fun PokemonResponse2.toData(): Pokemon {
    // TODO : 바꿔야 함 - 임시로 걍 바꿈
    val newFormName =
        formName.split("_").joinToString("_") {
            if (it.isBlank()) {
                it
            } else {
                it[0].uppercase() + it.substring(1).lowercase()
            }
        }

    val formattedName =
        if (formName.isBlank() || formName.trim().lowercase() == "empty" || formName.trim()
                .lowercase() == "normal"
        ) {
            name
        } else {
            "$name-$newFormName"
        }

    return Pokemon(
        id = id,
        dexNumber = pokedexNumber,
        name = formattedName,
        formName = formName,
        imageUrl = image,
        backImageUrl = backImage,
        types = types.map(PokemonTypeResponse::toData),
        generation = PokemonGeneration.of(generation),
        baseStat = baseStats,
        speed = speed,
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
    )
}

fun PokemonEntity.toData(): Pokemon =
    Pokemon(
        id = id,
        dexNumber = dexNumber,
        name = name,
        imageUrl = imageUrl,
        backImageUrl = backImageUrl,
        types = types.map(Type::valueOf),
        generation = PokemonGeneration.of(generation),
        baseStat = baseStat,
        speed = speed,
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
    )

fun Pokemon.toEntity(): PokemonEntity =
    PokemonEntity(
        id = id,
        dexNumber = dexNumber,
        name = name,
        formName = formName,
        imageUrl = imageUrl,
        backImageUrl = backImageUrl,
        types = types.map(Type::name).toSet(),
        generation = generation.number,
        baseStat = baseStat,
        speed = speed,
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
    )

fun List<PokemonResponse>.toData(): List<Pokemon> = map(PokemonResponse::toData)
