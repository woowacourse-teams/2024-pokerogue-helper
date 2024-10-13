package poke.rogue.helper.remote.di

import org.koin.dsl.module

val remoteModule
    get() = module {
        includes(retrofitModule, serviceModule)
    }
