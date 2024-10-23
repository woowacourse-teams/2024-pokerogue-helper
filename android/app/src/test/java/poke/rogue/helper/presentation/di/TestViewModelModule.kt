package poke.rogue.helper.presentation.di

import org.koin.core.module.dsl.singleOf
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
import poke.rogue.helper.testing.di.testingModule

val testViewModelModule =
    module {
        includes(testingModule)

        singleOf(::PokemonListViewModel)
        single { (pokemonId: String?, selectionType: SelectionType?) ->
            BattleViewModel(
                get(),
                get(),
                get(),
                pokemonId,
                selectionType,
            )
        }
        single { (selectionMode: SelectionMode, previousSelection: SelectionData) ->
            BattleSelectionViewModel(
                selectionMode,
                previousSelection,
                get(),
            )
        }
        single { (previousSelection: PokemonSelectionUiModel?) ->
            PokemonSelectionViewModel(
                get(),
                previousSelection,
                get(),
            )
        }
        single { (previousSelection: SelectionData.WithSkill?) ->
            SkillSelectionViewModel(
                get(),
                previousSelection,
                get(),
            )
        }
    }
