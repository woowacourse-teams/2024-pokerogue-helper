package poke.rogue.helper.data.repository

import kotlinx.coroutines.flow.Flow
import poke.rogue.helper.data.model.BattlePrediction
import poke.rogue.helper.data.model.BattleSkill
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonWithSkill
import poke.rogue.helper.data.model.Weather

interface BattleRepository {
    suspend fun weathers(): List<Weather>

    suspend fun availableSkills(dexNumber: Long): List<BattleSkill>

    suspend fun calculatedBattlePrediction(
        weatherId: String,
        myPokemonId: String,
        mySkillId: String,
        opponentPokemonId: String,
    ): BattlePrediction

    suspend fun saveBattleSelection(pokemonId: String)

    suspend fun saveBattleSelection(
        pokemonId: String,
        skillId: String,
    )

    suspend fun saveWeather(weatherId: String)

    fun weatherStream(): Flow<Weather?>

    fun pokemonStream(): Flow<Pokemon?>

    fun pokemonWithSkillStream(): Flow<PokemonWithSkill?>

    suspend fun pokemonWithSkill(pokemonId: String): PokemonWithSkill
}
