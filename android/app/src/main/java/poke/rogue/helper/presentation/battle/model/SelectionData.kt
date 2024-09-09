package poke.rogue.helper.presentation.battle.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.analytics.AnalyticsEvent
import poke.rogue.helper.presentation.battle.model.SelectionData.WithSkill
import poke.rogue.helper.presentation.battle.model.SelectionData.WithoutSkill

fun WithSkill.toAnalyticsParams(): List<AnalyticsEvent.Param> {
    return listOf(
        AnalyticsEvent.Param(key = "pokemon_id", value = selectedPokemon.id),
        AnalyticsEvent.Param(key = "skill_id", value = selectedSkill.id),
    )
}

fun WithoutSkill.toAnalyticsParams(): List<AnalyticsEvent.Param> {
    return listOf(
        AnalyticsEvent.Param(key = "pokemon_id", value = selectedPokemon.id),
    )
}

sealed class SelectionData : Parcelable {
    @Parcelize
    data class WithSkill(
        val selectedPokemon: PokemonSelectionUiModel,
        val selectedSkill: SkillSelectionUiModel,
    ) : SelectionData()

    @Parcelize
    data class WithoutSkill(
        val selectedPokemon: PokemonSelectionUiModel,
    ) : SelectionData()

    @Parcelize
    data object NoSelection : SelectionData()
}

fun SelectionData.selectedPokemon(): PokemonSelectionUiModel? {
    return when (this) {
        is SelectionData.NoSelection -> null
        is SelectionData.WithSkill -> this.selectedPokemon
        is SelectionData.WithoutSkill -> this.selectedPokemon
    }
}

fun SelectionData.selectedSkill(): SkillSelectionUiModel? {
    return when (this) {
        is SelectionData.NoSelection -> null
        is SelectionData.WithSkill -> this.selectedSkill
        is SelectionData.WithoutSkill -> null
    }
}
