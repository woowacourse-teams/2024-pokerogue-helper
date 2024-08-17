package poke.rogue.helper.remote.injector

import poke.rogue.helper.remote.service.AbilityService
import poke.rogue.helper.remote.service.BiomeService
import poke.rogue.helper.remote.service.PokeDexService
import retrofit2.create

object ServiceModule {
    fun pokeDexService(): PokeDexService = ServicePool.pokeDexService

    fun abilityService(): AbilityService = ServicePool.abilityService

    fun biomesService(): BiomeService = ServicePool.biomesService

    private object ServicePool {
        val pokeDexService: PokeDexService by lazy {
            RetrofitModule.retrofit().create()
        }
        val abilityService: AbilityService by lazy {
            RetrofitModule.retrofit().create()
        }
        val biomesService: BiomeService by lazy {
            RetrofitModule.retrofit().create()
        }
    }
}
