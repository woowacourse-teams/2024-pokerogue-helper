package poke.rogue.helper.presentation.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import poke.rogue.helper.presentation.battle.BattleViewModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.battle.selection.pokemon.PokemonSelectionViewModel
import poke.rogue.helper.presentation.battle.selection.skill.SkillSelectionViewModel
import poke.rogue.helper.presentation.biome.BiomeViewModel
import poke.rogue.helper.presentation.biome.detail.BiomeDetailViewModel
import poke.rogue.helper.presentation.dex.PokemonListViewModel
import poke.rogue.helper.presentation.type.TypeViewModel
import poke.rogue.helper.testing.di.testingModule

val testViewModelModule =
    module {
        includes(testingModule)

        singleOf(::PokemonListViewModel)
        viewModelOf(::TypeViewModel)
        viewModel<BattleViewModel> { params ->
            BattleViewModel(get(), get(), get(), params.getOrNull(), params.getOrNull())
        }
        viewModel<BattleSelectionViewModel> { params ->
            BattleSelectionViewModel(params.get(), params.get(), get())
        }
        viewModel { params ->
            PokemonSelectionViewModel(get(), params.getOrNull(), get())
        }
        viewModel { params ->
            SkillSelectionViewModel(get(), params.getOrNull(), get())
        }
        singleOf(::BiomeViewModel)
        singleOf(::BiomeDetailViewModel)
    }
