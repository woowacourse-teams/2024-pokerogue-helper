package poke.rogue.helper.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import poke.rogue.helper.local.converter.PokemonTypeConverters
import poke.rogue.helper.local.dao.PokemonDao
import poke.rogue.helper.local.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
)
@androidx.room.TypeConverters(PokemonTypeConverters::class)
abstract class PokeRogueDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        private const val DATABASE_NAME = "pokemon_helper.db"

        @Volatile
        private var instance: PokeRogueDatabase? = null

        fun instance(context: Context): PokeRogueDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    PokeRogueDatabase::class.java,
                    DATABASE_NAME,
                ).build().also { instance = it }
            }
        }
    }
}
