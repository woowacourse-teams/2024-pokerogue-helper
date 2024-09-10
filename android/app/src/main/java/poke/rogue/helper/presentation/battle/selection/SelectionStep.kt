package poke.rogue.helper.presentation.battle.selection

enum class SelectionStep {
    POKEMON_SELECTION,
    SKILL_SELECTION,
    ;

    fun isFirstStep(): Boolean = this.ordinal > 0

    fun isLastStep(hasSkillSelection: Boolean): Boolean {
        return if (hasSkillSelection) {
            this == SKILL_SELECTION
        } else {
            this == POKEMON_SELECTION
        }
    }
}
