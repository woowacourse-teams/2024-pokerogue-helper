package poke.rogue.helper.presentation.dex

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.toUi
import poke.rogue.helper.testing.CoroutinesTestExtension
import poke.rogue.helper.testing.data.repository.FakeDexRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class PokemonListViewModelTest {
    private lateinit var repository: DexRepository
    private lateinit var viewModel: PokemonListViewModel

    @BeforeEach
    fun setUp() {
        repository = FakeDexRepository()
    }

    @Test
    fun `모든 포켓몬 목록을 불러온다`() =
        runTest {
            // given
            viewModel = PokemonListViewModel(repository)

            // when
            val pokemons =
                viewModel.uiState.first { uiState ->
                    uiState.pokemons.isNotEmpty()
                }.pokemons

            // then
            pokemons shouldBe FakeDexRepository.POKEMONS.map(Pokemon::toUi)
        }

    @Test
    fun `포켓몬 이름으로 쿼리된 포켓몬 목록을 불러온다`() =
        runTest {
            // given
            viewModel = PokemonListViewModel(repository)

            // when
            viewModel.queryName("리자")
            val queriedPokemons =
                viewModel.uiState.first { uiState ->
                    uiState.pokemons.isNotEmpty()
                }.pokemons
            // then
            val actualIds = queriedPokemons.map(PokemonUiModel::id)
            actualIds shouldBe listOf("5", "6")
        }
}
