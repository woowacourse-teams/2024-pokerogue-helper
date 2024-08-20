package poke.rogue.helper.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pokemon")
data class PokemonEntity(
    @PrimaryKey val id: String,
    val dexNumber: Long,
    val formName: String,
    val name: String,
    val imageUrl: String,
    val types: Set<String>,
    val generation: Int,
    val baseStat: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
)
