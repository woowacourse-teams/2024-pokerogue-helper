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

    suspend fun savePokemon(pokemonId: String)

    suspend fun savePokemonWithSkill(
        pokemonId: String,
        skillId: String,
    )

    suspend fun saveWeather(weatherId: String)

    fun selectedWeatherStream(): Flow<Weather?>

    suspend fun savedPokemon(): Flow<Pokemon?>

    suspend fun savedPokemonWithSkill(): Flow<PokemonWithSkill?>
}
