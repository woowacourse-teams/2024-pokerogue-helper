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

data class SkillCategory2(
    val name: String,
    val logo: String,
) {
    companion object {
        val physicalAttackSkill =
            SkillCategory2(
                name = "물리",
                logo = "https://img.pokemondb.net/images/icons/move-physical.png",
            )

        val specialAttackSkill =
            SkillCategory2(
                name = "특수",
                logo = "https://img.pokemondb.net/images/icons/move-special.png",
            )

        val changeStatusSkill =
            SkillCategory2(
                name = "변화",
                logo = "https://img.pokemondb.net/images/icons/move-status.png",
            )
    }
}
