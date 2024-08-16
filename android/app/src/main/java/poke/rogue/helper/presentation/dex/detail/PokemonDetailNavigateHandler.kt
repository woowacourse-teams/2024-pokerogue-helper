package poke.rogue.helper.presentation.dex.detail

interface PokemonDetailNavigateHandler {
    fun navigateToAbilityDetail(abilityId: Long)

    fun navigateToBiomeDetail(biomeId: String)

    fun navigateToHome()
}
