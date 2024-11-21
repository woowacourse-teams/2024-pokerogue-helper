package poke.rogue.helper.presentation.dex.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.presentation.type.model.TypeUiModel1

@Parcelize
data class PokeFilterUiModel(
    val selectedTypes: List<TypeUiModel1>,
    val selectedGeneration: PokeGenerationUiModel,
) : Parcelable
