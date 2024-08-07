package poke.rogue.helper.presentation.dex.detail

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.dex.model.AbilityTitleUiModel
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.StatUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.testing.CoroutinesTestExtension
import poke.rogue.helper.testing.data.repository.FakeDexRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class PokemonDetailViewModelTest {
    private lateinit var repository: DexRepository
    private lateinit var viewModel: PokemonDetailViewModel

    @BeforeEach
    fun setUp() {
        repository = FakeDexRepository()
    }

    @Test
    fun `포켓몬 상세 데이터를 불러올 때 처음은 로딩 상태이다`() =
        runTest {
            // given
            viewModel = PokemonDetailViewModel(repository)

            // when
            val pokemonDetailUiState = viewModel.uiState

            // then
            pokemonDetailUiState.value shouldBe PokemonDetailUiState.IsLoading
        }

    @Test
    fun `포켓몬 상세 데이터를 불러온다`() =
        runTest {
            // given
            viewModel = PokemonDetailViewModel(repository)

            // when
            viewModel.updatePokemonDetail(pokemonId = 1)

            // then
            val pokemonDetailUiState = viewModel.uiState
            pokemonDetailUiState.value shouldBe
                PokemonDetailUiState.Success(
                    pokemon =
                        PokemonUiModel(
                            id = 1,
                            dexNumber = 1,
                            name = "이상해씨",
                            imageUrl =
                                "https://raw.githubusercontent.com" +
                                    "/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                            types =
                                listOf(
                                    TypeUiModel.GRASS,
                                    TypeUiModel.POISON,
                                ),
                        ),
                    stats =
                        listOf(
                            StatUiModel("HP", 45, 255, R.color.stat_hp),
                            StatUiModel("공격", 49, 190, R.color.stat_attack),
                            StatUiModel("방어", 49, 250, R.color.stat_defense),
                            StatUiModel("특수공격", 65, 194, R.color.stat_special_attack),
                            StatUiModel("특수방어", 65, 250, R.color.stat_special_defense),
                            StatUiModel("스피드", 45, 200, R.color.stat_speed),
                            StatUiModel("총합", 318, 800, R.color.stat_total),
                        ),
                    abilities =
                        listOf(
                            AbilityTitleUiModel(450, "심록"),
                            AbilityTitleUiModel(419, "엽록소"),
                        ),
                    height = 0.7f,
                    weight = 6.9f,
                )
        }
}