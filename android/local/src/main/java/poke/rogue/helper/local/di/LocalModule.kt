package poke.rogue.helper.local.di

import org.koin.dsl.module

val localModule = module {
    includes(dataBaseModule, daoModule, dataStoreModule)
}