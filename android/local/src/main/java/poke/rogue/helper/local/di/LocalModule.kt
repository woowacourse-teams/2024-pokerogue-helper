package poke.rogue.helper.local.di

import org.koin.dsl.module

val localModule
    get() =
        module {
            includes(dataBaseModule, daoModule, dataStoreModule)
        }
