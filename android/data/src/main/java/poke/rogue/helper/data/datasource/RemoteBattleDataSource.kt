package poke.rogue.helper.data.datasource

import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.data.exception.getOrThrow
import poke.rogue.helper.data.exception.onFailure
import poke.rogue.helper.data.model.BattlePrediction
import poke.rogue.helper.data.model.BattleSkill
import poke.rogue.helper.data.model.Weather
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.service.BattleService

class RemoteBattleDataSource(
    private val battleService: BattleService,
    private val logger: AnalyticsLogger,
) {
    suspend fun weathers(): List<Weather> =
        battleService.weathers()
            .onFailure {
                logger.logError(throwable, "battleService - weathers() 에서 발생")
            }
            .getOrThrow()
            .map { it.toData() }

    suspend fun availableSkills(dexNumber: Long): List<BattleSkill> =
        battleService.availableSkills(dexNumber)
            .onFailure {
                logger.logError(throwable, "battleService - availableSkills($dexNumber) 에서 발생")
            }
            .getOrThrow()
            .map { it.toData() }

    suspend fun calculatedBattlePrediction(
        weatherId: String,
        myPokemonId: String,
        mySkillId: String,
        opponentPokemonId: String,
    ): BattlePrediction =
        battleService.calculatedBattlePrediction(
            weatherId,
            myPokemonId,
            mySkillId,
            opponentPokemonId,
        ).onFailure {
            logger.logError(
                throwable,
                "battleService - calculatedBattlePrediction($weatherId, $myPokemonId, $mySkillId, $opponentPokemonId) 에서 발생",
            )
        }.getOrThrow().toData()
}
