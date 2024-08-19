package poke.rogue.helper.presentation.dex.detail

interface PokemonDetailNavigateHandler {
    fun navigateToAbilityDetail(abilityId: String)

    fun navigateToBiomeDetail(biomeId: String)

    fun navigateToHome()
}
