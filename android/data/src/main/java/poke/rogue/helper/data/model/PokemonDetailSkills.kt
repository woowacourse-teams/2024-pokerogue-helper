package poke.rogue.helper.data.model

data class PokemonDetailSkills(
    val selfLearn: List<PokemonSkill>,
    val eggLearn: List<PokemonSkill>,
    val tmLearn: List<PokemonSkill>,
)
