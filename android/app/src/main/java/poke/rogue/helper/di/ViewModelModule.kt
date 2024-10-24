package poke.rogue.helper.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import poke.rogue.helper.presentation.ability.AbilityViewModel
import poke.rogue.helper.presentation.ability.detail.AbilityDetailViewModel
import poke.rogue.helper.presentation.battle.BattleViewModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.battle.selection.pokemon.PokemonSelectionViewModel
import poke.rogue.helper.presentation.battle.selection.skill.SkillSelectionViewModel
import poke.rogue.helper.presentation.biome.BiomeViewModel
import poke.rogue.helper.presentation.biome.detail.BiomeDetailViewModel
import poke.rogue.helper.presentation.dex.PokemonListViewModel
import poke.rogue.helper.presentation.home.HomeViewModel
import poke.rogue.helper.presentation.type.TypeViewModel
import poke.rogue.helper.presentation.dex.detail.PokemonDetailViewModel
import poke.rogue.helper.presentation.splash.PokemonIntroViewModel

val viewModelModule
    get() =
        module {
            viewModelOf(::PokemonIntroViewModel)
            viewModelOf(::PokemonListViewModel)
            viewModelOf(::BiomeViewModel)
            viewModelOf(::BiomeDetailViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::AbilityViewModel)
            viewModelOf(::AbilityDetailViewModel)
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
            viewModelOf(::PokemonDetailViewModel)
        }
