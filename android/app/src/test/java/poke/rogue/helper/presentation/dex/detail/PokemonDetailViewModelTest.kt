package poke.rogue.helper.presentation.dex.detail

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import poke.rogue.helper.R
import poke.rogue.helper.data.model.PokemonBiome
import poke.rogue.helper.data.model.PokemonDetailSkills
import poke.rogue.helper.data.model.PokemonSkill
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.dex.model.EvolutionsUiModel
import poke.rogue.helper.presentation.dex.model.PokemonDetailAbilityUiModel
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.StatUiModel
import poke.rogue.helper.presentation.dex.model.toUi
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.testing.CoroutinesTestExtension
import poke.rogue.helper.testing.data.repository.FakeDexRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class PokemonDetailViewModelTest {
    private lateinit var dexRepository: DexRepository
    private lateinit var viewModel: PokemonDetailViewModel

    @BeforeEach
    fun setUp() {
        dexRepository = FakeDexRepository()
    }

    @Test
    fun `포켓몬 상세 데이터를 불러올 때 처음은 로딩 상태이다`() =
        runTest {
            // given
            viewModel =
                PokemonDetailViewModel(
                    dexRepository,
                )

            // when
            val expectedPokemonDetailUiState = viewModel.uiState

            // then
            expectedPokemonDetailUiState.value shouldBe PokemonDetailUiState.IsLoading
        }

    @Test
    fun `포켓몬 상세 데이터를 불러온다`() =
        runTest {
            // given
            viewModel =
                PokemonDetailViewModel(
                    dexRepository,
                )

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
                            StatUiModel("HP", 45, 255, R.color.stat_hp),
                            StatUiModel("공격", 49, 190, R.color.stat_attack),
                            StatUiModel("방어", 49, 250, R.color.stat_defense),
                            StatUiModel("특수공격", 65, 194, R.color.stat_special_attack),
                            StatUiModel("특수방어", 65, 250, R.color.stat_special_defense),
                            StatUiModel("스피드", 45, 200, R.color.stat_speed),
                            StatUiModel("종족값", 318, 800, R.color.stat_total),
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
}
