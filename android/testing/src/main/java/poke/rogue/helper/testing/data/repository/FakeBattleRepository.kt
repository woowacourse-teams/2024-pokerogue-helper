package poke.rogue.helper.testing.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import poke.rogue.helper.data.model.BattlePrediction
import poke.rogue.helper.data.model.BattleSkill
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonGeneration
import poke.rogue.helper.data.model.PokemonWithSkill
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.model.Weather
import poke.rogue.helper.data.repository.BattleRepository

class FakeBattleRepository : BattleRepository {
    override suspend fun weathers(): List<Weather> = DUMMY_WEATHERS

    override suspend fun availableSkills(dexNumber: Long): List<BattleSkill> = DUMMY_SKILLS

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

    override fun weatherStream(): Flow<Weather?> = flow { emit(DUMMY_WEATHERS[0]) }

    override fun pokemonStream(): Flow<Pokemon?> = flow { emit(DUMMY_POKEMON) }

    override fun pokemonWithSkillStream(): Flow<PokemonWithSkill?> =
        flow {
            emit(
                PokemonWithSkill(
                    DUMMY_POKEMON,
                    DUMMY_SKILL,
                ),
            )
        }

    override suspend fun pokemonWithSkill(pokemonId: String): PokemonWithSkill =
        PokemonWithSkill(
            DUMMY_POKEMON,
            DUMMY_SKILL,
        )

    companion object {
        val DUMMY_POKEMON =
            Pokemon(
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

        val DUMMY_SKILL =
            BattleSkill(
                id = "4",
                name = "Solar Beam",
                type = Type.GRASS,
                categoryLogo = "",
                power = 120,
                accuracy = 100,
                effect = "",
            )

        val DUMMY_BATTLE_RESULT = BattlePrediction(0, 0.0, 0.0, 0.0)

        val DUMMY_WEATHERS =
            listOf(
                Weather(
                    id = "none",
                    name = "없음",
                    description = "없음",
                    effects = listOf("없음"),
                ),
                Weather(
                    id = "sunny",
                    name = "쾌청",
                    description = "햇살이 강하다",
                    effects =
                        listOf(
                            "불꽃 타입 기술의 위력이 1.5배가 된다",
                            "물 타입 기술의 위력이 0.5배가 된다",
                        ),
                ),
                Weather(
                    id = "rain",
                    name = "비",
                    description = "비가 계속 내리고 있다",
                    effects =
                        listOf(
                            "물 타입 기술의 위력이 1.5배가 된다",
                            "불꽃 타입 기술의 위력이 0.5배가 된다",
                        ),
                ),
            )

        val DUMMY_SKILLS =
            listOf(
                BattleSkill(
                    id = "tackle",
                    name = "몸통박치기",
                    type = Type.GRASS,
                    categoryLogo = "",
                    power = 40,
                    accuracy = 100,
                    effect = "상대를 향해서 몸 전체를 부딪쳐가며 공격한다.",
                ),
                BattleSkill(
                    id = "growl",
                    name = "울음소리",
                    type = Type.NORMAL,
                    categoryLogo = "",
                    power = -1,
                    accuracy = 100,
                    effect = "귀여운 울음소리를 들려주고 관심을 끌어 방심한 사이에 상대의 공격을 떨어뜨린다.",
                ),
            )
    }
}
