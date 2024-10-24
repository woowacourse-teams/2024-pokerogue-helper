package poke.rogue.helper.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import poke.rogue.helper.presentation.battle.BattleViewModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.battle.selection.pokemon.PokemonSelectionViewModel
import poke.rogue.helper.presentation.battle.selection.skill.SkillSelectionViewModel
import poke.rogue.helper.presentation.dex.PokemonListViewModel
import poke.rogue.helper.presentation.type.TypeViewModel

val viewModelModule
    get() =
        module {
            viewModelOf(::PokemonListViewModel)
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
            viewModelOf(::TypeViewModel)
        }
