package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemoteBattleDataSource
import poke.rogue.helper.data.model.BattlePrediction
import poke.rogue.helper.data.model.BattleSkill
import poke.rogue.helper.data.model.Weather

class DefaultBattleRepository(private val remoteBattleDataSource: RemoteBattleDataSource) : BattleRepository {
    override suspend fun weathers(): List<Weather> = remoteBattleDataSource.weathers()

    override suspend fun availableSkills(dexNumber: Long): List<BattleSkill> = remoteBattleDataSource.availableSkills(dexNumber)

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

    companion object {
        private var instance: BattleRepository? = null

        fun instance(): BattleRepository {
            return instance ?: DefaultBattleRepository(RemoteBattleDataSource.instance()).also {
                instance = it
            }
        }
    }
}
