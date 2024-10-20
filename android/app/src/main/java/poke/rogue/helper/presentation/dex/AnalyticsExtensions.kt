package poke.rogue.helper.presentation.dex

import poke.rogue.helper.analytics.AnalyticsEvent
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.presentation.dex.detail.EnemyPokemon
import poke.rogue.helper.presentation.dex.detail.MyPokemon
import poke.rogue.helper.presentation.dex.detail.PokemonDetailToBattleEvent
import poke.rogue.helper.presentation.dex.filter.PokeFilterUiModel
import poke.rogue.helper.presentation.dex.sort.PokemonSortUiModel

fun AnalyticsLogger.logPokemonSort(sort: PokemonSortUiModel) {
    val eventType = "pokemon_dex_sort"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras = sort.toParams(),
        ),
    )
}

private fun PokemonSortUiModel.toParams(): List<AnalyticsEvent.Param> {
    return listOf(
        AnalyticsEvent.Param(key = "sort_type", value = name),
    )
}

fun AnalyticsLogger.logPokemonFilter(filter: PokeFilterUiModel) {
    val eventType = "pokemon_dex_filter"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras = filter.toParams(),
        ),
    )
}

private fun PokeFilterUiModel.toParams(): List<AnalyticsEvent.Param> {
    return selectedTypes.map {
        AnalyticsEvent.Param(key = "type", value = it.name)
    } + AnalyticsEvent.Param(key = "generation", value = selectedGeneration.name)
}

fun AnalyticsLogger.logPokemonDetailToBattle(event: PokemonDetailToBattleEvent) {
    val eventType = "pokemon_detail_to_battle_directly"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras = event.toParams(),
        ),
    )
}

private fun PokemonDetailToBattleEvent.toParams(): List<AnalyticsEvent.Param> {
    val battleRoleValue =
        when (battlePopUp) {
            is MyPokemon -> "MyPokemon"
            is EnemyPokemon -> "EnemyPokemon"
        }
    return listOf(
        AnalyticsEvent.Param(key = "battle_role", value = battleRoleValue),
        AnalyticsEvent.Param(key = "pokemon_id", value = pokemon.id),
        AnalyticsEvent.Param(key = "pokemon_name", value = pokemon.name),
    )
}
