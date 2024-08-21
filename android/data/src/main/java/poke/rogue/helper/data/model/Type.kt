package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse
import java.util.Locale

enum class Type(val id: Int) {
    NORMAL(0),
    FIRE(1),
    WATER(2),
    ELECTRIC(3),
    GRASS(4),
    ICE(5),
    FIGHTING(6),
    POISON(7),
    GROUND(8),
    FLYING(9),
    PSYCHIC(10),
    BUG(11),
    ROCK(12),
    GHOST(13),
    DRAGON(14),
    DARK(15),
    STEEL(16),
    FAIRY(17),
    STELLAR(18),
    ;

    companion object {
        private val typeById = entries.associateBy { it.id }

        fun fromId(id: Int): Type {
            return typeById[id] ?: throw IllegalArgumentException("Unknown type ID: $id")
        }

        fun of(name: String): Type {
            return valueOf(name.uppercase(Locale.ROOT))
        }
    }
}

fun PokemonTypeResponse.toData(): Type = Type.of(pokemonTypeName)

fun List<PokemonTypeResponse>.toData(): List<Type> = map(PokemonTypeResponse::toData)
