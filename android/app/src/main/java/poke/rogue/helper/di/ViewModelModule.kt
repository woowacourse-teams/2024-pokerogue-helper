package poke.rogue.helper.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import poke.rogue.helper.presentation.dex.PokemonListViewModel
import poke.rogue.helper.presentation.dex.detail.PokemonDetailViewModel
import poke.rogue.helper.presentation.splash.PokemonIntroViewModel

val viewModelModule
    get() =
        module {
            viewModelOf(::PokemonIntroViewModel)
            viewModelOf(::PokemonListViewModel)
            viewModelOf(::PokemonDetailViewModel)
        }
