package poke.rogue.helper.local.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import poke.rogue.helper.local.datastore.BattleDataStore
import poke.rogue.helper.local.datastore.NavigationModeDataStore

internal val dataStoreModule
    get() =
        module {
            singleOf(::BattleDataStore)
            singleOf(::NavigationModeDataStore)
        }
