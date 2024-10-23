package poke.rogue.helper.remote.injector

import poke.rogue.helper.remote.service.AbilityService
import poke.rogue.helper.remote.service.BattleService
import poke.rogue.helper.remote.service.BiomeService
import retrofit2.create

object ServiceModule {
    fun abilityService(): AbilityService = ServicePool.abilityService

    fun biomeService(): BiomeService = ServicePool.biomeService

    fun battleService(): BattleService = ServicePool.battleService

    private object ServicePool {
        val abilityService: AbilityService by lazy {
            RetrofitModule.retrofit().create()
        }
        val biomeService: BiomeService by lazy {
            RetrofitModule.retrofit().create()
        }
        val battleService: BattleService by lazy {
            RetrofitModule.retrofit().create()
        }
    }
}
