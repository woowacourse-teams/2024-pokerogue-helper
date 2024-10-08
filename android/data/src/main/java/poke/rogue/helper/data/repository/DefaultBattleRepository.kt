package poke.rogue.helper.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import poke.rogue.helper.data.datasource.LocalBattleDataSource
import poke.rogue.helper.data.datasource.RemoteBattleDataSource
import poke.rogue.helper.data.model.BattlePrediction
import poke.rogue.helper.data.model.BattleSkill
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonWithSkill
import poke.rogue.helper.data.model.Weather

class DefaultBattleRepository(
    private val localBattleDataSource: LocalBattleDataSource,
    private val remoteBattleDataSource: RemoteBattleDataSource,
    private val pokemonRepository: DexRepository,
) : BattleRepository {
    private val cachedSkills: MutableMap<Long, List<BattleSkill>> = hashMapOf()

    override suspend fun weathers(): List<Weather> = remoteBattleDataSource.weathers()

    override suspend fun availableSkills(dexNumber: Long): List<BattleSkill> =
        cachedSkills[dexNumber] ?: run {
            val skills = remoteBattleDataSource.availableSkills(dexNumber).distinct()
            cachedSkills[dexNumber] = skills
            skills
        }

    override suspend fun calculatedBattlePrediction(
        weatherId: String,
        myPokemonId: String,
        mySkillId: String,
        opponentPokemonId: String,
    ): BattlePrediction =
        remoteBattleDataSource.calculatedBattlePrediction(
            weatherId = weatherId,
            myPokemonId = myPokemonId,
            mySkillId = mySkillId,
            opponentPokemonId = opponentPokemonId,
        )

    override suspend fun savePokemon(pokemonId: String) = localBattleDataSource.savePokemon(pokemonId)

    override suspend fun savePokemonWithSkill(
        pokemonId: String,
        skillId: String,
    ) = localBattleDataSource.savePokemonWithSkill(pokemonId, skillId)

    override fun savedPokemonStream(): Flow<Pokemon?> = localBattleDataSource.pokemonIdStream().map { it?.let { pokemonRepository.pokemon(it) } }

    override fun savedPokemonWithSkillStream(): Flow<PokemonWithSkill?> =
        localBattleDataSource.pokemonWithSkillStream().map {
            it?.let { pokemonWithSkill ->
                val pokemon = pokemonRepository.pokemon(pokemonWithSkill.pokemonId)
                val skill = skill(pokemon.dexNumber, pokemonWithSkill.skillId)
                PokemonWithSkill(pokemon, skill)
            }
        }

    override suspend fun saveWeather(weatherId: String) = localBattleDataSource.saveWeather(weatherId)

    override fun savedWeatherStream(): Flow<Weather?> =
        localBattleDataSource.weatherIdStream().map {
            if (it == null) {
                return@map null
            }
            weathers().find { weather -> weather.id == it }
        }

    private suspend fun skill(
        dexNumber: Long,
        skillId: String,
    ): BattleSkill =
        availableSkills(dexNumber).find {
            it.id == skillId
        } ?: error("아이디에 해당하는 스킬이 존재하지 않습니다. id: $skillId")

    companion object {
        private var instance: BattleRepository? = null

        fun instance(context: Context): BattleRepository {
            return instance ?: DefaultBattleRepository(
                LocalBattleDataSource.instance(context),
                RemoteBattleDataSource.instance(),
                DefaultDexRepository.instance(),
            ).also {
                instance = it
            }
        }
    }
}
