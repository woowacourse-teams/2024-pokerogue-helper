package poke.rogue.helper.presentation.ability.model

import poke.rogue.helper.data.model.AbilityDetail
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.toUi

class AbilityDetailUiModel(
    val title: String,
    val description: String,
    val pokemons: List<PokemonUiModel>,
) {
    companion object {
        private const val DUMMY_ABILITY_NAME = "악취"
        private const val DUMMY_ABILITY_DESCRIPTION = "악취를 풍겨 상대방의 특성을 무효화 시킵니다."

        val DUMMY =
            AbilityDetailUiModel(
                title = DUMMY_ABILITY_NAME,
                description = DUMMY_ABILITY_DESCRIPTION,
                pokemons = emptyList(),
            )
    }
}

fun AbilityDetail.toUi(): AbilityDetailUiModel =
    AbilityDetailUiModel(
        title = this.title,
        description = this.description,
        pokemons = this.pokemons.map { it.toUi() },
    )
