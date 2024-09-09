package poke.rogue.helper.presentation.battle

import poke.rogue.helper.analytics.AnalyticsEvent
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.presentation.battle.model.SelectionData

fun AnalyticsLogger.logPokemonSkillSelection(selection: SelectionData.WithSkill) {
    val eventType = "pokemon_battle_skill_selection"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras = selection.toAnalyticsParams(),
        ),
    )
}

fun AnalyticsLogger.logBattlePokemonSelection(selection: SelectionData.WithoutSkill) {
    val eventType = "pokemon_battle_selection"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras = selection.toAnalyticsParams(),
        ),
    )
}

private fun SelectionData.WithoutSkill.toAnalyticsParams(): List<AnalyticsEvent.Param> {
    return listOf(
        AnalyticsEvent.Param(key = "pokemon_id", value = selectedPokemon.id),
        AnalyticsEvent.Param(key = "pokemon_name", value = selectedPokemon.name),
    )
}

private fun SelectionData.WithSkill.toAnalyticsParams(): List<AnalyticsEvent.Param> {
    return listOf(
        AnalyticsEvent.Param(key = "pokemon_id", value = selectedPokemon.id),
        AnalyticsEvent.Param(key = "pokemon_name", value = selectedPokemon.name),
        AnalyticsEvent.Param(key = "skill_id", value = selectedSkill.id),
        AnalyticsEvent.Param(key = "skill_name", value = selectedSkill.name),
    )
}
