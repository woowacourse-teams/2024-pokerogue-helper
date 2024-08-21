package poke.rogue.helper.data.model

data class PokemonDetailSkills(
    val selfLearn: List<PokemonSkill>,
    val eggLearn: List<PokemonSkill>,
    val tmLearn: List<PokemonSkill>,
)

data class PokemonDetailSKills2(
    val selfLearn: List<PokemonSkill2>,
    val eggLearn: List<PokemonSkill2>,
    val tmLearn: List<PokemonSkill2>,
)
