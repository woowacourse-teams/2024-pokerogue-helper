package poke.rogue.helper.data.datasource

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import poke.rogue.helper.data.model.PokemonWithSkillIds
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.local.datastore.BattleDataStore

class LocalBattleDataSource(private val battleDataStore: BattleDataStore) {
    suspend fun savePokemonWithSkill(
        pokemonId: String,
        skillId: String,
    ) {
        battleDataStore.savePokemonWithSkill(pokemonId, skillId)
    }

    suspend fun savePokemon(pokemonId: String) {
        battleDataStore.savePokemon(pokemonId)
    }

    suspend fun saveWeather(weatherId: String) {
        battleDataStore.saveWeather(weatherId)
    }

    fun weatherId(): Flow<String?> = battleDataStore.weatherId()

    fun pokemonWithSkill(): Flow<PokemonWithSkillIds?> = battleDataStore.pokemonWithSkillId().map { it?.toData() }

    fun pokemonId(): Flow<String?> = battleDataStore.pokemonId()

    companion object {
        private var instance: LocalBattleDataSource? = null

        fun instance(context: Context): LocalBattleDataSource {
            return instance ?: LocalBattleDataSource(
                BattleDataStore(context),
            ).also {
                instance = it
            }
        }
    }
}
