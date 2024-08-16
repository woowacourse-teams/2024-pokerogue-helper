package poke.rogue.helper.data.model

data class NewPokemonSkills(
    val selfLearn: List<NewSkill>,
    val eggLearn: List<NewSkill>,
    val tmLearn: List<NewSkill>,
)
