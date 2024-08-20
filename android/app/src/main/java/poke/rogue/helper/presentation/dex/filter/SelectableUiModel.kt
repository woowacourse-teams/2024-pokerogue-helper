package poke.rogue.helper.presentation.dex.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectableUiModel<T : Parcelable>(
    val id: Int,
    val isSelected: Boolean,
    val data: T,
) : Parcelable
