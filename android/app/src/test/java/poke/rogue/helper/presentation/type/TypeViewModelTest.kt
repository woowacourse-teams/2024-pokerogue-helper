package poke.rogue.helper.presentation.type

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import poke.rogue.helper.data.repository.TypeRepository
import poke.rogue.helper.presentation.type.model.MatchedResultUiModel
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel1
import poke.rogue.helper.testing.CoroutinesTestExtension
import poke.rogue.helper.testing.data.repository.FakeTypeRepository

@ExperimentalCoroutinesApi
@ExtendWith(CoroutinesTestExtension::class)
class TypeViewModelTest {
    private lateinit var typeRepository: TypeRepository
    private lateinit var viewModel: TypeViewModel

    @BeforeEach
    fun setup() {
        typeRepository = FakeTypeRepository()
        viewModel = TypeViewModel(typeRepository)
    }

    @Test
    fun `ViewModel이 초기화 될 때, 모든 타입 선택 상태는 Empty 이다 `() =
        runTest {
            val expected = TypeSelectionUiState.Empty
            val actualStates = viewModel.typeSelectionStates.value

            actualStates.myType shouldBe expected
            actualStates.opponentType1 shouldBe expected
            actualStates.opponentType2 shouldBe expected
        }

    @Test
    fun `내 타입을 선택하는 경우, 선택된 타입을 알 수 있다`() =
        runTest {
            // given
            val selectedMyType = TypeUiModel1.FAIRY

            // when
            viewModel.selectType(SelectorType.MINE, selectedMyType)

            // then
            val expected = TypeSelectionUiState.Selected(TypeUiModel1.FAIRY)
            val actual = viewModel.typeSelectionStates.value.myType
            actual shouldBe expected
        }

    @Test
    fun `내 타입만 선택된 경우, 내 타입에 대한 모든 상성 결과를 불러온다`() =
        runTest {
            // given
            val selectedMyType = TypeUiModel1.FAIRY

            // when
            viewModel.selectType(SelectorType.MINE, selectedMyType)

            // then
            launch {
                viewModel.type.collect { actual ->
                    val expected =
                        listOf(
                            MatchedTypesUiModel(
                                TypeUiModel1.FAIRY,
                                true,
                                MatchedResultUiModel.STRONG,
                                listOf(TypeUiModel1.ICE, TypeUiModel1.DRAGON),
                            ),
                            MatchedTypesUiModel(
                                TypeUiModel1.FAIRY,
                                true,
                                MatchedResultUiModel.WEAK,
                                listOf(TypeUiModel1.FIRE, TypeUiModel1.POISON),
                            ),
                        )
                    actual shouldBe expected
                    cancel()
                }
            }
        }

    @Test
    fun `상대 타입 1개만 선택하는 경우, 해당 타입에 대한 모든 상성 결과를 불러온다`() =
        runTest {
            // given
            val selectedType = TypeUiModel1.FAIRY

            // when
            viewModel.selectType(SelectorType.OPPONENT1, selectedType)

            // then
            launch {
                viewModel.type.collect { actual ->
                    val expected =
                        listOf(
                            MatchedTypesUiModel(
                                TypeUiModel1.FAIRY,
                                false,
                                MatchedResultUiModel.STRONG,
                                listOf(TypeUiModel1.POISON, TypeUiModel1.STEEL),
                            ),
                            MatchedTypesUiModel(
                                TypeUiModel1.FAIRY,
                                false,
                                MatchedResultUiModel.NORMAL,
                                listOf(TypeUiModel1.WATER, TypeUiModel1.GRASS),
                            ),
                        )
                    actual shouldBe expected
                    cancel()
                }
            }
        }

    @Test
    fun `내 타입과 상대 타입 모두를 선택하면, 두 타입의 상성 결과를 불러온다`() =
        runTest {
            // given
            val myType = TypeUiModel1.FAIRY
            val opponentType = TypeUiModel1.FIGHTING

            // when
            viewModel.selectType(SelectorType.MINE, myType)
            viewModel.selectType(SelectorType.OPPONENT1, opponentType)

            // then
            launch {
                viewModel.type.collect { actual ->
                    val expected =
                        listOf(
                            MatchedTypesUiModel(
                                TypeUiModel1.FAIRY,
                                true,
                                MatchedResultUiModel.STRONG,
                                listOf(TypeUiModel1.FIGHTING),
                            ),
                        )

                    actual shouldBe expected
                    cancel()
                }
            }
        }
}
