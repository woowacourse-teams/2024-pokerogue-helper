package poke.rogue.helper.data.model

data class NewPokemon(
    val id: String,
    val dexNumber: Int,
    val name: String,
    val imageUrl: String,
    val types: List<Type>,
)
