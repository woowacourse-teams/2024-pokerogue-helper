package poke.rogue.helper.presentation.dex.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectableUiModel<T : Parcelable>(
    val id: Int,
    val isSelected: Boolean,
    val data: T,
) : Parcelable

fun <T : Parcelable> List<T>.toSelectableModelsWithAllDeselected(): List<SelectableUiModel<T>> {
    return this.mapIndexed { index, t -> SelectableUiModel(index, false, t) }
}

fun <T : Parcelable> List<SelectableUiModel<T>>.updateSelectionBy(predicate: (T) -> Boolean): List<SelectableUiModel<T>> {
    return this.map {
        val isSelected = predicate(it.data)
        it.copy(isSelected = isSelected)
    }
}

fun <T : Parcelable> List<T>.toSelectableModelsBy(predicate: (T) -> Boolean): List<SelectableUiModel<T>> {
    return this.mapIndexed { index, t ->
        val isSelected = predicate(t)
        SelectableUiModel(id = index, isSelected = isSelected, t)
    }
}
