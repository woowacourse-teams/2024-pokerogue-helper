package poke.rogue.helper.presentation.battle.model

enum class SelectionMode {
    POKEMON_ONLY,
    POKEMON_AND_SKILL,
    SKILL_FIRST,
}

fun SelectionMode.isSkillSelectionRequired(): Boolean = this != SelectionMode.POKEMON_ONLY
