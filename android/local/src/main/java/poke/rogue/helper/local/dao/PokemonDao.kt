package poke.rogue.helper.local.dao

import android.content.Context
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import poke.rogue.helper.local.db.PokeRogueDatabase
import poke.rogue.helper.local.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM Pokemon")
    suspend fun pokemons(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemons(pokemons: List<PokemonEntity>)

    companion object {
        fun instance(context: Context): PokemonDao {
            // TODO 삭제될 블록
            PokeRogueDatabase.dropDatabase(context)
            return PokeRogueDatabase.instance(context).pokemonDao()
        }
    }
}
