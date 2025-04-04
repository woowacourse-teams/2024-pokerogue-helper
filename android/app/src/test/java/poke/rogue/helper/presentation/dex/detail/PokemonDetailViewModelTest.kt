package poke.rogue.helper.presentation.dex.detail

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.component.get
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension
import poke.rogue.helper.R
import poke.rogue.helper.data.model.PokemonBiome
import poke.rogue.helper.data.model.PokemonDetailSkills
import poke.rogue.helper.data.model.PokemonSkill
import poke.rogue.helper.presentation.dex.model.EvolutionsUiModel
import poke.rogue.helper.presentation.dex.model.PokemonDetailAbilityUiModel
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.StatUiModel
import poke.rogue.helper.presentation.dex.model.toUi
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.testing.CoroutinesTestExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class PokemonDetailViewModelTest : KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension =
        KoinTestExtension.create {
            modules(testViewModelModule)
        }

    private val viewModel: PokemonDetailViewModel
        get() = get<PokemonDetailViewModel>()

    @Test
    fun `포켓몬 상세 데이터를 불러올 때 처음은 로딩 상태이다`() =
        runTest {
            // when
            val expectedPokemonDetailUiState = viewModel.uiState

            // then
            expectedPokemonDetailUiState.value shouldBe PokemonDetailUiState.IsLoading
        }

    @Test
    fun `포켓몬 상세 데이터를 불러온다`() =
        runTest {
            // when
            viewModel.updatePokemonDetail(pokemonId = "1")

            // then
            val pokemonDetailUiState =
                viewModel.uiState.first { uiState ->
                    uiState is PokemonDetailUiState.Success
                }

            pokemonDetailUiState shouldBe
                PokemonDetailUiState.Success(
                    pokemon =
                        PokemonUiModel(
                            id = "1",
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
                            StatUiModel(R.string.pokemon_stat_hp, 45, 255, R.color.stat_hp),
                            StatUiModel(R.string.pokemon_stat_attack, 49, 190, R.color.stat_attack),
                            StatUiModel(R.string.pokemon_stat_defense, 49, 250, R.color.stat_defense),
                            StatUiModel(R.string.pokemon_stat_special_attack, 65, 194, R.color.stat_special_attack),
                            StatUiModel(R.string.pokemon_stat_special_defense, 65, 250, R.color.stat_special_defense),
                            StatUiModel(R.string.pokemon_stat_speed, 45, 200, R.color.stat_speed),
                            StatUiModel(R.string.pokemon_stat_base_stat, 318, 800, R.color.stat_total),
                        ),
                    abilities =
                        listOf(
                            PokemonDetailAbilityUiModel("10", "그래스메이커", true, false),
                            PokemonDetailAbilityUiModel("450", "심록", false, false),
                            PokemonDetailAbilityUiModel("419", "엽록소", false, true),
                        ),
                    evolutions =
                        EvolutionsUiModel.DUMMY_PICAKCHU_EVOLUTION,
                    skills =
                        PokemonDetailSkills(
                            selfLearn = PokemonSkill.FAKE_SELF_LEARN_SKILLS,
                            eggLearn = PokemonSkill.FAKE_EGG_LEARN_SKILLS,
                            tmLearn = PokemonSkill.FAKE_SELF_LEARN_SKILLS,
                        ),
                    height = 0.7f,
                    weight = 6.9f,
                    biomes = PokemonBiome.DUMMYS.toUi(),
                )
        }

    @Test
    fun `홈으로 이동한다`() =
        runTest {
            // when
            viewModel.navigateToHome()

            // then
            val event =
                viewModel.navigationEvent.first {
                    it !is PokemonDetailViewModel.NavigationEvent.None
                }
            event shouldBe PokemonDetailViewModel.NavigationEvent.ToHome
        }

    @Test
    fun `특성 상세로 이동한다`() =
        runTest {
            // when
            viewModel.navigateToAbilityDetail("10")

            // then
            val event =
                viewModel.navigationEvent.first {
                    it !is PokemonDetailViewModel.NavigationEvent.None
                }
            event shouldBe PokemonDetailViewModel.NavigationEvent.ToAbilityDetail("10")
        }

    @Test
    fun `바이옴 상세로 이동한다`() =
        runTest {
            // when
            viewModel.navigateToBiomeDetail("10")

            // then
            val event =
                viewModel.navigationEvent.first {
                    it !is PokemonDetailViewModel.NavigationEvent.None
                }
            event shouldBe PokemonDetailViewModel.NavigationEvent.ToBiomeDetail("10")
        }

    @Test
    fun `현재 포켓몬을 선택하여 포켓몬 상세로 이동하려고 하면 이동에 실패한다`() =
        runTest {
            // given
            viewModel.updatePokemonDetail("1")

            // when
            viewModel.navigateToPokemonDetail("1")

            // then
            val event =
                viewModel.navigationEvent.first {
                    it !is PokemonDetailViewModel.NavigationEvent.None
                }
            event shouldBe
                PokemonDetailViewModel.NavigationEvent.ToPokemonDetail.Failure(
                    "이상해씨",
                )
        }

    @Test
    fun `현재 포켓몬이 아닌 포켓몬을 선택하여 포켓몬 상세로 이동한다`() =
        runTest {
            // given
            viewModel.updatePokemonDetail("1")

            // when
            viewModel.navigateToPokemonDetail("2")

            // then
            val event =
                viewModel.navigationEvent.first {
                    it !is PokemonDetailViewModel.NavigationEvent.None
                }
            event shouldBe
                PokemonDetailViewModel.NavigationEvent.ToPokemonDetail.Success(
                    "2",
                )
        }

    @Test
    fun `현재 포켓몬을 나의 포켓몬으로 하여 배틀 페이지로 이동한다`() =
        runTest {
            // given
            viewModel.updatePokemonDetail("1")

            // when
            viewModel.navigateToBattleWithMine()

            // then
            val event =
                viewModel.navigationEvent.first {
                    it !is PokemonDetailViewModel.NavigationEvent.None
                }
            (event as PokemonDetailViewModel.NavigationEvent.ToBattle.WithMyPokemon).pokemon.id shouldBe "1"
        }

    @Test
    fun `현재 포켓몬을 상대 포켓몬으로 하여 배틀 페이지로 이동한다`() =
        runTest {
            // given
            viewModel.updatePokemonDetail("1")

            // when
            viewModel.navigateToBattleWithOpponent()

            // then
            val event =
                viewModel.navigationEvent.first {
                    it !is PokemonDetailViewModel.NavigationEvent.None
                }
            (event as PokemonDetailViewModel.NavigationEvent.ToBattle.WithOpponentPokemon).pokemon.id shouldBe "1"
        }
}
