package poke.rogue.helper.presentation.dex.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class EvolutionsUiModelTest {
    @Test
    fun `포켓몬 개체의 진화 체인이 있다 `() {
        // given
        val evolutionsUiModel =
            EvolutionsUiModel(
                SingleEvolutionUiModel(
                    pokemonId = "psyduck",
                    pokemonName = "고라파덕",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/54.png",
                    depth = 0,
                ),
                SingleEvolutionUiModel(
                    pokemonId = "golduck",
                    pokemonName = "골덕",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/55.png",
                    depth = 1,
                    level = 33,
                ),
            )

        // when
        val expected = evolutionsUiModel.hasEvolutionChain()

        // then
        expected shouldBe true
    }
}
