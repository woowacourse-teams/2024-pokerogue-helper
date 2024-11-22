package poke.rogue.helper.presentation.dex.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.presentation.type.model.TypeUiModel

@Parcelize
data class PokeFilterUiModel(
    val selectedTypes: List<TypeUiModel>,
    val selectedGeneration: PokeGenerationUiModel,
) : Parcelable
