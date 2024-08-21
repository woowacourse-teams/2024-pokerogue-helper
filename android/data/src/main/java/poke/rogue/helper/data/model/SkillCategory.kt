package poke.rogue.helper.data.model

data class SkillCategory(
    val name: String,
    val logo: String,
) {
    companion object {
        val physicalAttackSkill =
            SkillCategory(
                name = "물리",
                logo = "https://img.pokemondb.net/images/icons/move-physical.png",
            )

        val specialAttackSkill =
            SkillCategory(
                name = "특수",
                logo = "https://img.pokemondb.net/images/icons/move-special.png",
            )

        val changeStatusSkill =
            SkillCategory(
                name = "변화",
                logo = "https://img.pokemondb.net/images/icons/move-status.png",
            )
    }
}
