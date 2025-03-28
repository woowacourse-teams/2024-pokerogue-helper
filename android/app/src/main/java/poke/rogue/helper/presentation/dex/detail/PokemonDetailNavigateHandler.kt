package poke.rogue.helper.presentation.dex.detail

interface PokemonDetailNavigateHandler {
    fun navigateToPokemonList()

    fun navigateToAbilityDetail(abilityId: String)

    fun navigateToBiomeDetail(biomeId: String)

    fun navigateToHome()

    fun navigateToPokemonDetail(pokemonId: String)

    fun navigateToBattleWithMine()

    fun navigateToBattleWithOpponent()
}
