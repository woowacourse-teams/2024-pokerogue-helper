package poke.rogue.helper.data.model

data class SkillCategory(
    val id: String,
    val name: String,
) {
    companion object {
        val physicalAttackSkill =
            SkillCategory(
                id = "1",
                name = "물리",
            )

        val specialAttackSkill =
            SkillCategory(
                id = "2",
                name = "특수",
            )

        val changeStatusSkill =
            SkillCategory(
                id = "3",
                name = "변화",
            )
    }
}
