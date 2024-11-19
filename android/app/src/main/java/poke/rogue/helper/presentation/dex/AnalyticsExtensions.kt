package poke.rogue.helper.presentation.dex

import poke.rogue.helper.analytics.AnalyticsEvent
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.presentation.dex.detail.NavigateToBattleEvent
import poke.rogue.helper.presentation.dex.filter.PokeFilterUiModel
import poke.rogue.helper.presentation.dex.sort.PokemonSortUiModel1

fun AnalyticsLogger.logPokemonSort(sort: PokemonSortUiModel1) {
    val eventType = "pokemon_dex_sort"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras = sort.toParams(),
        ),
    )
}

private fun PokemonSortUiModel1.toParams(): List<AnalyticsEvent.Param> {
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

fun AnalyticsLogger.logPokemonDetailToBattle(event: NavigateToBattleEvent) {
    val eventType = "pokemon_detail_to_battle_directly"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras = event.toParams(),
        ),
    )
}

private fun NavigateToBattleEvent.toParams(): List<AnalyticsEvent.Param> {
    val (battleRoleValue, pokemon) =
        when (this) {
            is NavigateToBattleEvent.WithMyPokemon -> Pair("MyPokemon", pokemon)
            is NavigateToBattleEvent.WithOpponentPokemon -> Pair("EnemyPokemon", pokemon)
        }

    return listOf(
        AnalyticsEvent.Param(key = "battle_role", value = battleRoleValue),
        AnalyticsEvent.Param(key = "pokemon_id", value = pokemon.id),
        AnalyticsEvent.Param(key = "pokemon_name", value = pokemon.name),
    )
}
