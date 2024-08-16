package poke.rogue.helper.data.model

data class NewPokemonDetail(
    val pokemon: NewPokemon,
    val abilities: List<NewAbility>,
    val stats: List<Stat>,
    val pokemonCategory: PokemonCategory,
    val height: Double,
    val weight: Double,
    val evolutions: List<Evolution>,
    val skills: NewPokemonSkills,
)
