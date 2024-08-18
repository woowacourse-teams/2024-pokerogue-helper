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
import poke.rogue.helper.presentation.type.model.TypeUiModel
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
            queriedPokemons shouldBe
                listOf(
                    PokemonUiModel(
                        id = 5,
                        dexNumber = 5,
                        name = "리자드",
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png",
                        types = listOf(TypeUiModel.FIRE),
                    ),
                    PokemonUiModel(
                        id = 6,
                        dexNumber = 6,
                        name = "리자몽",
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
                        types = listOf(TypeUiModel.FIRE, TypeUiModel.FLYING),
                    ),
                )
        }
}
