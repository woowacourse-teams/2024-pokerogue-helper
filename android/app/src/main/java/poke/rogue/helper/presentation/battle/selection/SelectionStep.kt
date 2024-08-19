package poke.rogue.helper.presentation.battle.selection

enum class SelectionStep(val buttonText: String) {
    POKEMON_SELECTION("다음"),
    SKILL_SELECTION("선택 완료"),
    ;

    fun isLastStep(hasSkillSelection: Boolean): Boolean {
        return if (hasSkillSelection) {
            this == SKILL_SELECTION
        } else {
            this == POKEMON_SELECTION
        }
    }
}
