package poke.rogue.helper.remote.dto.response.type

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import poke.rogue.helper.data.model.Type

class PokemonTypeResponseTest {
    @ParameterizedTest
    @CsvSource(
        "fire, FIRE",
        "water, WATER",
        "grass, GRASS",
        "electric, ELECTRIC",
    )
    fun `포켓몬 타입 응답을 데이터로 매핑한다`(
        responseTypeName: String,
        dataTypeName: String,
    ) {
        // given
        val typeResponse =
            PokemonTypeResponse(
                pokemonTypeName = responseTypeName,
                pokemonTypeLogo = "logo",
            )

        // then
        typeResponse.toData() shouldBe Type.valueOf(dataTypeName)
    }
}
