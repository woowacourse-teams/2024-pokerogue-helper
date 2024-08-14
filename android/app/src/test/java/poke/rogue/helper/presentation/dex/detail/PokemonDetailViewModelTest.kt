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
import poke.rogue.helper.presentation.dex.model.PokemonSkillUiModel
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.SkillCategoryUiModel
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
                    skills =
                        listOf(
                            PokemonSkillUiModel(
                                id = 1,
                                name = "몸통박치기",
                                level = 1,
                                power = "40",
                                type = TypeUiModel.NORMAL,
                                accuracy = 100,
                                category = SkillCategoryUiModel(1, "물리", R.drawable.ic_physical_attack_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 2,
                                name = "울음소리",
                                level = 1,
                                power = PokemonSkillUiModel.NO_POWER,
                                type = TypeUiModel.NORMAL,
                                accuracy = 100,
                                category = SkillCategoryUiModel(3, "변화", R.drawable.ic_change_status_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 3,
                                name = "덩굴채찍",
                                level = 1,
                                power = "45",
                                type = TypeUiModel.GRASS,
                                accuracy = 100,
                                category = SkillCategoryUiModel(1, "물리", R.drawable.ic_physical_attack_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 4,
                                name = "성장",
                                level = 6,
                                power = PokemonSkillUiModel.NO_POWER,
                                type = TypeUiModel.NORMAL,
                                accuracy = 100,
                                category = SkillCategoryUiModel(3, "변화", R.drawable.ic_change_status_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 5,
                                name = "씨뿌리기",
                                level = 9,
                                power = PokemonSkillUiModel.NO_POWER,
                                type = TypeUiModel.GRASS,
                                accuracy = 90,
                                category = SkillCategoryUiModel(3, "변화", R.drawable.ic_change_status_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 6,
                                name = "앞날가르기",
                                level = 12,
                                power = "55",
                                type = TypeUiModel.GRASS,
                                accuracy = 95,
                                SkillCategoryUiModel(1, "물리", R.drawable.ic_physical_attack_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 7,
                                name = "독가루",
                                level = 15,
                                power = PokemonSkillUiModel.NO_POWER,
                                type = TypeUiModel.POISON,
                                accuracy = 75,
                                category = SkillCategoryUiModel(3, "변화", R.drawable.ic_change_status_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 8,
                                name = "수면가루",
                                level = 15,
                                power = PokemonSkillUiModel.NO_POWER,
                                type = TypeUiModel.GRASS,
                                accuracy = 75,
                                category = SkillCategoryUiModel(3, "변화", R.drawable.ic_change_status_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 9,
                                name = "씨폭탄",
                                level = 18,
                                power = "80",
                                type = TypeUiModel.GRASS,
                                accuracy = 100,
                                category = SkillCategoryUiModel(1, "물리", R.drawable.ic_physical_attack_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 10,
                                name = "돌진",
                                level = 21,
                                power = "90",
                                type = TypeUiModel.NORMAL,
                                accuracy = 85,
                                category = SkillCategoryUiModel(1, "물리", R.drawable.ic_physical_attack_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 11,
                                name = "달콤한향기",
                                level = 24,
                                power = PokemonSkillUiModel.NO_POWER,
                                type = TypeUiModel.NORMAL,
                                accuracy = 100,
                                category = SkillCategoryUiModel(3, "변화", R.drawable.ic_change_status_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 12,
                                name = "광합성",
                                level = 27,
                                power = PokemonSkillUiModel.NO_POWER,
                                type = TypeUiModel.GRASS,
                                accuracy = 100,
                                category = SkillCategoryUiModel(3, "변화", R.drawable.ic_change_status_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 13,
                                name = "고민씨",
                                level = 30,
                                power = PokemonSkillUiModel.NO_POWER,
                                type = TypeUiModel.GRASS,
                                accuracy = 100,
                                category = SkillCategoryUiModel(3, "변화", R.drawable.ic_change_status_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 14,
                                name = "파워휩",
                                level = 33,
                                power = "120",
                                type = TypeUiModel.GRASS,
                                accuracy = 85,
                                category = SkillCategoryUiModel(1, "물리", R.drawable.ic_physical_attack_skill),
                            ),
                            PokemonSkillUiModel(
                                id = 15,
                                name = "솔라빔",
                                level = 36,
                                power = "120",
                                type = TypeUiModel.GRASS,
                                accuracy = 100,
                                category = SkillCategoryUiModel(2, "특수", R.drawable.ic_special_attack_skill),
                            ),
                        ),
                    height = 0.7f,
                    weight = 6.9f,
                )
        }
}
