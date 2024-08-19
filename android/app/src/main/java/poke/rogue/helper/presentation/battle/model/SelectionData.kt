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
}
