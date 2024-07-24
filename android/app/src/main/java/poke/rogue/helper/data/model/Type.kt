package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse
import java.util.Locale

enum class Type {
    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY,
}

fun PokemonTypeResponse.toData(): Type = Type.valueOf(pokemonTypeName.uppercase(Locale.ROOT))

fun List<PokemonTypeResponse>.toData(): List<Type> = map(PokemonTypeResponse::toData)
