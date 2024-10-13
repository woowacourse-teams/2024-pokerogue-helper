package poke.rogue.helper.di

import org.koin.dsl.module
import poke.rogue.helper.analytics.di.analyticsModule
import poke.rogue.helper.data.di.dataModule

val appModule
    get() = module {
        includes(dataModule, analyticsModule, viewModelModule)
    }