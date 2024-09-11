package poke.rogue.helper.data.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import poke.rogue.helper.data.model.fixture.pokemonTypeResponse
import poke.rogue.helper.data.model.fixture.pokemonTypeResponses

class TypeMapperTest {
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
        val typeResponse = pokemonTypeResponse(responseTypeName)

        // then
        typeResponse.toData() shouldBe Type.valueOf(dataTypeName)
    }

    @Test
    fun `포켓몬 타입 응답 목록을 데이터로 매핑한다`() {
        // given
        val typeResponses = pokemonTypeResponses("fire", "water")

        // then
        typeResponses.toData() shouldBe listOf(Type.FIRE, Type.WATER)
    }
}
