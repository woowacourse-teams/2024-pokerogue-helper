package poke.rogue.helper.local.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import poke.rogue.helper.local.datastore.BattleDataStore

internal val dataStoreModule
    get() = module {
    singleOf(::BattleDataStore)
}