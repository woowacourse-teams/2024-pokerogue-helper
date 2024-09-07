package poke.rogue.helper.presentation.battle.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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

fun SelectionData.selectedPokemonOrNull(): PokemonSelectionUiModel? {
    return when (this) {
        is SelectionData.NoSelection -> null
        is SelectionData.WithSkill -> this.selectedPokemon
        is SelectionData.WithoutSkill -> this.selectedPokemon
    }
}

fun SelectionData.selectedSkillOrNull(): SkillSelectionUiModel? {
    return when (this) {
        is SelectionData.NoSelection -> null
        is SelectionData.WithSkill -> this.selectedSkill
        is SelectionData.WithoutSkill -> null
    }
}
