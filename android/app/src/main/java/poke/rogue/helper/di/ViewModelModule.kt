package poke.rogue.helper.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import poke.rogue.helper.presentation.ability.AbilityViewModel
import poke.rogue.helper.presentation.ability.detail.AbilityDetailViewModel
import poke.rogue.helper.presentation.dex.PokemonListViewModel
import poke.rogue.helper.presentation.home.HomeViewModel

val viewModelModule
    get() =
        module {
            viewModelOf(::PokemonListViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::AbilityViewModel)
            viewModelOf(::AbilityDetailViewModel)
        }
