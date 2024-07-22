package poke.rogue.helper.data.model

data class PokemonDetail(
    val pokemon: Pokemon,
    val stats: List<Stat>,
    val abilities: List<String>,
    val height: Float,
    val weight: Float,
)
