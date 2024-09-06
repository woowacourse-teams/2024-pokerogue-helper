package poke.rogue.helper.data.utils

import poke.rogue.helper.analytics.AnalyticsEvent
import poke.rogue.helper.analytics.AnalyticsLogger

internal fun AnalyticsLogger.logPokemonDetail(pokemonId: String, name: String) {
    val eventType = "pokemon_dex_detail"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras = listOf(
                AnalyticsEvent.Param(key = eventType.toParamKey(), value = pokemonId),
                AnalyticsEvent.Param(key = eventType.toNameParamKey(), value = name),
            ),
        ),
    )
}

internal fun AnalyticsLogger.logBiomeDetail(biomeId: String, name: String) {
    val eventType = "biome_detail"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras = listOf(
                AnalyticsEvent.Param(key = eventType.toParamKey(), value = biomeId),
                AnalyticsEvent.Param(key = eventType.toNameParamKey(), value = name),
            ),
        ),
    )
}

internal fun AnalyticsLogger.logBattlePokemon(pokemonId: String, name: String) {
    val eventType = "battle_pokemon"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras = listOf(
                AnalyticsEvent.Param(key = eventType.toParamKey(), value = pokemonId),
                AnalyticsEvent.Param(key = eventType.toNameParamKey(), value = name),
            ),
        ),
    )
}

internal fun AnalyticsLogger.logAbilityDetail(abilityId: String, name: String) {
    val eventType = "ability_detail"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras = listOf(
                AnalyticsEvent.Param(key = eventType.toParamKey(), value = abilityId),
                AnalyticsEvent.Param(key = eventType.toNameParamKey(), value = name),
            ),
        ),
    )
}

private fun String.toParamKey(): String {
    return "${this}_key"
}

private fun String.toNameParamKey(): String {
    return "${this}_name_key"
}

