package poke.rogue.helper.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import poke.rogue.helper.data.repository.AbilityRepository
import poke.rogue.helper.data.repository.BattleRepository
import poke.rogue.helper.data.repository.BiomeRepository
import poke.rogue.helper.data.repository.DefaultAbilityRepository
import poke.rogue.helper.data.repository.DefaultBattleRepository
import poke.rogue.helper.data.repository.DefaultBiomeRepository
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.data.repository.DefaultTypeRepository
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.data.repository.TypeRepository

internal val repositoryModule
    get() =
        module {
            singleOf(::DefaultBattleRepository).bind<BattleRepository>()
            singleOf(::DefaultAbilityRepository).bind<AbilityRepository>()
            singleOf(::DefaultDexRepository).bind<DexRepository>()
            singleOf(::DefaultBiomeRepository).bind<BiomeRepository>()
            singleOf(::DefaultTypeRepository).bind<TypeRepository>()
        }
