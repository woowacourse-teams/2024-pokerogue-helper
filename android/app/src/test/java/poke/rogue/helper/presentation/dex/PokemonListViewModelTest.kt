package poke.rogue.helper.presentation.dex

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.toUi
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.CoroutinesTestExtension
import poke.rogue.helper.testing.data.repository.FakeDexRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class PokemonListViewModelTest : KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(testViewModelModule)
    }

    private val viewModel: PokemonListViewModel
        get() = get<PokemonListViewModel>()

    @Test
    fun `모든 포켓몬 목록을 불러온다`() =
        runTest {
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
