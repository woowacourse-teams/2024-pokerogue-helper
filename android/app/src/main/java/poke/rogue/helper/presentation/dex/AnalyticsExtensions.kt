package poke.rogue.helper.presentation.dex

import poke.rogue.helper.analytics.AnalyticsEvent
import poke.rogue.helper.analytics.AnalyticsLogger
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
