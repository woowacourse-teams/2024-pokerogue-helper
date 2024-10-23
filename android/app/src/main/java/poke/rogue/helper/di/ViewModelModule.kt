package poke.rogue.helper.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import poke.rogue.helper.presentation.battle.BattleViewModel
import poke.rogue.helper.presentation.battle.SelectionType
import poke.rogue.helper.presentation.dex.PokemonListViewModel

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
        }
