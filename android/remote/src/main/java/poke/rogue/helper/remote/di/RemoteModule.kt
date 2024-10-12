package poke.rogue.helper.remote.di

import org.koin.dsl.module

val remoteModule = module {
    includes(retrofitModule, serviceModule)
}
