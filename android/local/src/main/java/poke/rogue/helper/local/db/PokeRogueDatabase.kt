package poke.rogue.helper.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import poke.rogue.helper.local.converter.PokemonTypeConverters
import poke.rogue.helper.local.dao.PokemonDao
import poke.rogue.helper.local.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 2,
)
@androidx.room.TypeConverters(PokemonTypeConverters::class)
abstract class PokeRogueDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        const val DATABASE_NAME = "pokemon_helper.db"
    }
}
