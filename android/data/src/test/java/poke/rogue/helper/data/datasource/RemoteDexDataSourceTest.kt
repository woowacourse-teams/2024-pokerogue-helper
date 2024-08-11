package poke.rogue.helper.data.datasource

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.data.model.fixture.pokemonResponses
import poke.rogue.helper.data.model.fixture.pokemons
import poke.rogue.helper.remote.dto.base.ApiResponse
import poke.rogue.helper.remote.service.PokeDexService

class RemoteDexDataSourceTest {
    private val mockService = mockk<PokeDexService>()
    private val dataSource = RemoteDexDataSource(mockService, AnalyticsLogger.Stub)

    @Test
    fun `모든 포켓몬 목록을 불러온다`() =
        runTest {
            // given
            val mockPokemonResponses = pokemonResponses(1, 2, 3, 4, 5)
            val mockResponse = mockPokemonResponses
            coEvery { mockService.pokemons() } returns ApiResponse.Success(mockResponse)

            // when
            val actualPokemonDatas = dataSource.pokemons()

            // then
            val expectedPokemonDatas = pokemons(1, 2, 3, 4, 5)
            actualPokemonDatas shouldBe expectedPokemonDatas
        }
}
