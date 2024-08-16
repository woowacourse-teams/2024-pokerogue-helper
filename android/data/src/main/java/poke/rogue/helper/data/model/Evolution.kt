package poke.rogue.helper.data.model

data class Evolution(
    val pokemonId: String,
    val pokemonName: String,
    val level: Int,
    val item: String?,
    val condition: String?,
)
