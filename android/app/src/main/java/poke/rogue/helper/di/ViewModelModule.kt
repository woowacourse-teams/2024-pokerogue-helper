package poke.rogue.helper.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import poke.rogue.helper.presentation.battle.BattleViewModel
import poke.rogue.helper.presentation.battle.SelectionType
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.SelectionMode
import poke.rogue.helper.presentation.battle.selection.BattleSelectionViewModel
import poke.rogue.helper.presentation.battle.selection.pokemon.PokemonSelectionViewModel
import poke.rogue.helper.presentation.battle.selection.skill.SkillSelectionViewModel
import poke.rogue.helper.presentation.dex.PokemonListViewModel
import poke.rogue.helper.presentation.type.TypeViewModel

val viewModelModule
    get() =
        module {
            viewModelOf(::PokemonListViewModel)
            viewModel<BattleViewModel> { (pokemonId: String?, selectionType: SelectionType?) ->
                BattleViewModel(
                    get(),
                    get(),
                    get(),
                    pokemonId,
                    selectionType,
                )
            }
            viewModel<BattleSelectionViewModel> { (selectionMode: SelectionMode, previousSelection: SelectionData) ->
                BattleSelectionViewModel(
                    selectionMode,
                    previousSelection,
                    get(),
                )
            }
            viewModel { (previousSelection: PokemonSelectionUiModel?) ->
                PokemonSelectionViewModel(
                    get(),
                    previousSelection,
                    get(),
                )
            }
            viewModel { (previousSelection: SelectionData.WithSkill?) ->
                SkillSelectionViewModel(
                    get(),
                    previousSelection,
                    get(),
                )
            }
            viewModelOf(::TypeViewModel)
        }
