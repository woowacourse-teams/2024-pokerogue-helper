package poke.rogue.helper.remote.service

import poke.rogue.helper.remote.dto.base.ApiResponse
import poke.rogue.helper.remote.dto.response.battle.BattlePredictionResponse
import poke.rogue.helper.remote.dto.response.battle.PokemonSkillResponse
import poke.rogue.helper.remote.dto.response.battle.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BattleService {
    @GET("api/v1/weathers")
    suspend fun weathers(): ApiResponse<List<WeatherResponse>>

    @GET("api/v1/moves")
    suspend fun availableSkills(
        @Query("pokedex-number") dexNumber: Long,
    ): ApiResponse<List<PokemonSkillResponse>>

    @GET("api/v1/battle")
    suspend fun calculatedBattlePrediction(
        @Query("weather-id") weatherId: String,
        @Query("my-pokemon-id") myPokemonId: String,
        @Query("my-move-id") mySkillId: String,
        @Query("rival-pokemon-id") opponentPokemonId: String,
    ): ApiResponse<BattlePredictionResponse>
}
