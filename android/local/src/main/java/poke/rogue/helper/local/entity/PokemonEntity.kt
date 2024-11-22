package poke.rogue.helper.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import poke.rogue.helper.local.entity.PokemonEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class PokemonEntity(
    @PrimaryKey val id: String,
    val dexNumber: Long,
    val formName: String,
    val name: String,
    val imageUrl: String,
    @ColumnInfo(defaultValue = "")
    val backImageUrl: String,
    val types: Set<String>,
    val generation: Int,
    val baseStat: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
) {
    companion object {
        const val TABLE_NAME = "Pokemon"
    }
}
