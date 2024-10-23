package poke.rogue.helper.testing.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import poke.rogue.helper.data.model.BattlePrediction
import poke.rogue.helper.data.model.BattleSkill
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonGeneration
import poke.rogue.helper.data.model.PokemonWithSkill
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.model.Weather
import poke.rogue.helper.data.repository.BattleRepository

class FakeBattleRepository : BattleRepository {
    override suspend fun weathers(): List<Weather> = emptyList()

    override suspend fun availableSkills(dexNumber: Long): List<BattleSkill> = emptyList()

    override suspend fun calculatedBattlePrediction(
        weatherId: String,
        myPokemonId: String,
        mySkillId: String,
        opponentPokemonId: String,
    ): BattlePrediction = DUMMY_BATTLE_RESULT

    override suspend fun saveBattleSelection(pokemonId: String) {}

    override suspend fun saveBattleSelection(
        pokemonId: String,
        skillId: String,
    ) {
    }

    override suspend fun saveWeather(weatherId: String) {}

    override fun weatherStream(): Flow<Weather?> = flowOf()

    override fun pokemonStream(): Flow<Pokemon?> = flowOf()

    override fun pokemonWithSkillStream(): Flow<PokemonWithSkill?> = flowOf()

    override suspend fun pokemonWithSkill(pokemonId: String): PokemonWithSkill =
        PokemonWithSkill(
            DUMMY_POKEMON,
            DUMMY_SKILL,
        )

    companion object {
        val DUMMY_POKEMON = Pokemon(
            id = "1",
            dexNumber = 1,
            name = "이상해씨",
            imageUrl = "",
            backImageUrl = "",
            types = listOf(Type.GRASS, Type.POISON),
            generation = PokemonGeneration.ONE,
            baseStat = 318,
            hp = 45,
            attack = 49,
            defense = 49,
            specialAttack = 65,
            specialDefense = 65,
            speed = 45,
        )

        val DUMMY_SKILL = BattleSkill(
            id = "4",
            name = "Solar Beam",
            type = Type.GRASS,
            categoryLogo = "",
            power = 120,
            accuracy = 100,
            effect = "",
        )

        val DUMMY_BATTLE_RESULT = BattlePrediction(0, 0.0, 0.0, 0.0)
    }
}
