package poke.rogue.helper.local.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

class PokemonTypeConverters {
    @TypeConverter
    fun fromTypeList(value: Set<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toTypeList(json: String): Set<String> {
        return json.split(",").toSet()
    }
}
