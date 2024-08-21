package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse
import java.util.Locale

enum class Type(val id: Int, val koName: String) {
    NORMAL(0, "노말"),
    FIRE(1, "불꽃"),
    WATER(2, "물"),
    ELECTRIC(3, "전기"),
    GRASS(4, "풀"),
    ICE(5, "얼음"),
    FIGHTING(6, "격투"),
    POISON(7, "독"),
    GROUND(8, "땅"),
    FLYING(9, "비행"),
    PSYCHIC(10, "에스퍼"),
    BUG(11, "벌레"),
    ROCK(12, "바위"),
    GHOST(13, "고스트"),
    DRAGON(14, "드래곤"),
    DARK(15, "악"),
    STEEL(16, "강철"),
    FAIRY(17, "페어리"),
    STELLAR(18, "스텔라"),
    ;

    companion object {
        private val typeById = entries.associateBy { it.id }

        fun fromId(id: Int): Type {
            return typeById[id] ?: throw IllegalArgumentException("Unknown type ID: $id")
        }

        fun of(name: String): Type {
            val type = entries.first { it.name == name.uppercase(Locale.ROOT).trim() || it.koName == name }
            return type
        }
    }
}

fun PokemonTypeResponse.toData(): Type = Type.of(typeName)

fun List<PokemonTypeResponse>.toData(): List<Type> = map(PokemonTypeResponse::toData)
