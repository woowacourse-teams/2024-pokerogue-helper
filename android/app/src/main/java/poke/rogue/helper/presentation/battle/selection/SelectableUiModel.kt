package poke.rogue.helper.presentation.battle.selection

data class SelectableUiModel<T : Any>(
    val id: Int,
    val isSelected: Boolean,
    val data: T,
)
