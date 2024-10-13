package poke.rogue.helper.remote.di

import org.koin.dsl.module
import poke.rogue.helper.remote.service.AbilityService
import poke.rogue.helper.remote.service.BattleService
import poke.rogue.helper.remote.service.BiomeService
import poke.rogue.helper.remote.service.PokeDexService
import retrofit2.Retrofit
import retrofit2.create

internal val serviceModule
    get() =
        module {
            single<AbilityService> { get<Retrofit>().create() }
            single<BattleService> { get<Retrofit>().create() }
            single<BiomeService> { get<Retrofit>().create() }
            single<PokeDexService> { get<Retrofit>().create() }
        }
