package poke.rogue.helper.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import poke.rogue.helper.local.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM Pokemon")
    suspend fun pokemons(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemons(pokemons: List<PokemonEntity>)

    @Query("DELETE FROM Pokemon")
    suspend fun clear()
}
