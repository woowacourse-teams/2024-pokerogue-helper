package poke.rogue.helper.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import poke.rogue.helper.presentation.dex.PokemonListViewModel

val viewModelModule
    get() =
        module {
            viewModelOf(::PokemonListViewModel)
        }
