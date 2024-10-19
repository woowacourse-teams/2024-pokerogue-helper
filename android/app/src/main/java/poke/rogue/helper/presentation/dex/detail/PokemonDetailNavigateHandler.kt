package poke.rogue.helper.presentation.dex.detail

interface PokemonDetailNavigateHandler: BattlePopUpHandler {
    fun navigateToAbilityDetail(abilityId: String)

    fun navigateToBiomeDetail(biomeId: String)

    fun navigateToHome()

    fun navigateToPokemonDetail(pokemonId: String)

    override fun navigateToBattle(battlePopUpUiModel: BattlePopUpUiModel)
}
