package poke.rogue.helper.remote.dto.response.pokemon

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.utils.fixture.pokemonResponses
import poke.rogue.helper.utils.fixture.pokemonTypeResponses

class PokemonResponseTest {
    @Test
    fun `포켓몬 응답을 데이터로 매핑한다`() {
        // given
        val pokemonResponse =
            PokemonResponse(
                id = 1,
                image = "image",
                name = "name",
                pokedexNumber = 1,
                types =
                    pokemonTypeResponses(
                        "fire",
                        "water",
                    ),
            )

        // when
        val expectedPokemonData =
            Pokemon(
                id = 1,
                dexNumber = 1,
                name = "name",
                imageUrl = "image",
                types = listOf(Type.FIRE, Type.WATER),
            )

        // then
        pokemonResponse.toData() shouldBe expectedPokemonData
    }

    @Test
    fun `포켓몬 응답 목록을 데이터로 매핑한다`() {
        // given
        val pokemonResponses = pokemonResponses(1, 2, 3)

        // when
        val expectedPokemonDatas =
            listOf(
                Pokemon(
                    id = 1,
                    dexNumber = 10,
                    name = "pokemon1",
                    imageUrl = "logo1",
                    types = listOf(Type.GRASS, Type.POISON),
                ),
                Pokemon(
                    id = 2,
                    dexNumber = 20,
                    name = "pokemon2",
                    imageUrl = "logo2",
                    types = listOf(Type.GRASS, Type.POISON),
                ),
                Pokemon(
                    id = 3,
                    dexNumber = 30,
                    name = "pokemon3",
                    imageUrl = "logo3",
                    types = listOf(Type.GRASS, Type.POISON),
                ),
            )

        // then
        pokemonResponses.toData() shouldBe expectedPokemonDatas
    }
}
