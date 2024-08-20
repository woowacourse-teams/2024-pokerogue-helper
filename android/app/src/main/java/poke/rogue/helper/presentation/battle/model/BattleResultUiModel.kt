package poke.rogue.helper.presentation.battle.model

data class BattleResultUiModel(val power: Long, val accuracy: Long) {
    companion object {
        val DUMMY = BattleResultUiModel(power = 30L, accuracy = 100L)
    }
}
