package poke.rogue.helper.presentation.dex.filter

data class SelectableUiModel<T : Any>(
    val id: Int,
    val isSelected: Boolean,
    val data: T,
)