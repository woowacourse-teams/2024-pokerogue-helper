package poke.rogue.helper.data.datasource

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

    fun weatherIdStream(): Flow<String?> = battleDataStore.weatherId()

    fun pokemonWithSkillStream(): Flow<PokemonWithSkillIds?> = battleDataStore.pokemonWithSkillId().map { it?.toData() }

    fun pokemonIdStream(): Flow<String?> = battleDataStore.pokemonId()
}
