package poke.rogue.helper.presentation.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import poke.rogue.helper.presentation.dex.PokemonListViewModel
import poke.rogue.helper.presentation.biome.BiomeViewModel
import poke.rogue.helper.presentation.biome.detail.BiomeDetailViewModel
import poke.rogue.helper.testing.di.testingModule

val testViewModelModule =
    module {
        includes(testingModule)

        singleOf(::PokemonListViewModel)
        singleOf(::BiomeViewModel)
        singleOf(::BiomeDetailViewModel)
    }
