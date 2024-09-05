package poke.rogue.helper.presentation.dex.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.PokemonBiome
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.presentation.type.model.toUi

class PokemonBiomeUiModelTest {
    @Test
    fun `포켓몬 바이옴 ui 모델 테스트`() {
        // given
        val pokemonBiomes = PokemonBiome.DUMMYS
        val allBiomes =
            listOf(
                Biome(
                    id = "1",
                    name = "평야",
                    image = "1",
                    pokemonType = listOf(Type.GRASS, Type.BUG),
                    gymLeaderType = listOf(Type.GRASS),
                ),
                Biome(
                    id = "2",
                    name = "높은 풀숲",
                    image = "1",
                    pokemonType = listOf(Type.BUG),
                    gymLeaderType = listOf(Type.GRASS),
                ),
            )

        // when
        val result = pokemonBiomes.toUi(allBiomes)

        // then
        result shouldBe
            listOf(
                PokemonBiomeUiModel(
                    id = "1",
                    name = "평야",
                    imageUrl = "1",
                    types = listOf(Type.GRASS.toUi(), Type.BUG.toUi()),
                ),
                PokemonBiomeUiModel(
                    id = "2",
                    name = "높은 풀숲",
                    imageUrl = "1",
                    types = listOf(Type.BUG.toUi()),
                ),
            )
    }
}
