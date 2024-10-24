package poke.rogue.helper.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import poke.rogue.helper.presentation.biome.BiomeViewModel
import poke.rogue.helper.presentation.biome.detail.BiomeDetailViewModel
import poke.rogue.helper.presentation.dex.PokemonListViewModel

val viewModelModule
    get() =
        module {
            viewModelOf(::PokemonListViewModel)
            viewModelOf(::BiomeViewModel)
            viewModelOf(::BiomeDetailViewModel)
        }
