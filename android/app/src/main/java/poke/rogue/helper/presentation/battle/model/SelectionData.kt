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
    data class NoSelection(val isSkillSelectionRequired: Boolean) : SelectionData()
}

fun SelectionData.isSkillSelectionRequired(): Boolean =
    this is SelectionData.WithSkill || (this as? SelectionData.NoSelection)?.isSkillSelectionRequired != null
