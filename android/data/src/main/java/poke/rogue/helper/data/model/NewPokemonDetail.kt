package poke.rogue.helper.data.model

data class NewPokemonDetail(
    val pokemon: NewPokemon,
    val abilities: List<NewAbility>,
    val stats: List<Stat>,
    val pokemonCategory: PokemonCategory,
    val evolutions: List<Evolution>,
    val skills: NewPokemonSkills,
    val biomes: List<Biome>,
    val height: Double,
    val weight: Double,
)
