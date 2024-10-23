package poke.rogue.helper.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import poke.rogue.helper.presentation.dex.PokemonListViewModel
import poke.rogue.helper.presentation.ability.AbilityViewModel
import poke.rogue.helper.presentation.ability.detail.AbilityDetailViewModel

val viewModelModule
    get() =
        module {
            viewModelOf(::PokemonListViewModel)
            viewModelOf(::AbilityViewModel)
            viewModelOf(::AbilityDetailViewModel)
        }
