package poke.rogue.helper.testing.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.data.repository.AbilityRepository
import poke.rogue.helper.data.repository.BiomeRepository
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.data.repository.TypeRepository
import poke.rogue.helper.testing.TestAnalyticsLogger
import poke.rogue.helper.testing.data.repository.FakeAbilityRepository
import poke.rogue.helper.testing.data.repository.FakeBiomeRepository
import poke.rogue.helper.testing.data.repository.FakeDexRepository
import poke.rogue.helper.testing.data.repository.FakeTypeRepository


val testingModule
    get() = module {
        includes(fakeRepositoryModule, fakeAnalyticsModule)
    }

private val fakeRepositoryModule
    get() = module {
        singleOf(::FakeAbilityRepository).bind<AbilityRepository>()
        singleOf(::FakeDexRepository).bind<DexRepository>()
        singleOf(::FakeBiomeRepository).bind<BiomeRepository>()
        singleOf(::FakeTypeRepository).bind<TypeRepository>()
    }

private val fakeAnalyticsModule
    get() = module {
        singleOf(::TestAnalyticsLogger).bind<AnalyticsLogger>()
    }
