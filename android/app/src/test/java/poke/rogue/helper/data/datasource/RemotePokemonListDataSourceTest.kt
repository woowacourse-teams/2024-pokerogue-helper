package poke.rogue.helper.data.datasource

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import poke.rogue.helper.remote.dto.response.BaseResponse
import poke.rogue.helper.remote.service.PokeDexService
import poke.rogue.helper.utils.fixture.pokemonResponses
import poke.rogue.helper.utils.fixture.pokemons

class RemotePokemonListDataSourceTest {
    private val mockService = mockk<PokeDexService>()
    private val dataSource = RemotePokemonListDataSource(mockService)

    @Test
    fun `모든 포켓몬 목록을 불러온다`() =
        runTest {
            // given
            val mockPokemonResponses = pokemonResponses(1, 2, 3, 4, 5)
            val mockResponse = BaseResponse(message = "포켓몬 리스트 불러오기에 성공했습니다.", data = mockPokemonResponses)
            coEvery { mockService.pokemons() } returns mockResponse

            // when
            val actualPokemonDatas = dataSource.pokemons()

            // then
            val expectedPokemonDatas = pokemons(1, 2, 3, 4, 5)
            actualPokemonDatas shouldBe expectedPokemonDatas
        }

    @Test
    fun `포켓몬 이름으로 쿼리된 포켓몬 목록을 불러온다`() =
        runTest {
            // given
            val query = "1"
            val mockPokemonResponses = pokemonResponses(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            val mockResponse = BaseResponse(message = "포켓몬 리스트 불러오기에 성공했습니다.", data = mockPokemonResponses)
            coEvery { mockService.pokemons() } returns mockResponse

            // when
            val actualPokemonDatas = dataSource.pokemons(query)

            // then
            val expectedPokemonDatas = pokemons(1, 10)
            actualPokemonDatas shouldBe expectedPokemonDatas
        }
}
