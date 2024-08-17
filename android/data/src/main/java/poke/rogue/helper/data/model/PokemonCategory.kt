package poke.rogue.helper.data.model

data class PokemonCategory(
    val legendary: Boolean,
    val subLegendary: Boolean,
    val mythical: Boolean,
    val canChangeForm: Boolean,
){
    companion object {
        val EMPTY = PokemonCategory(
            legendary = false,
            subLegendary = false,
            mythical = false,
            canChangeForm = false,
        )
    }
}
