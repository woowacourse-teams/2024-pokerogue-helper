package poke.rogue.helper.presentation.type

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.repository.TypeRepository
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModelComparator
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi
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
            val selectedMyType = TypeUiModel.FAIRY

            // when
            viewModel.selectType(SelectorType.MINE, selectedMyType)

            // then
            val expected = TypeSelectionUiState.Selected(TypeUiModel.FAIRY)
            val actual = viewModel.typeSelectionStates.value.myType
            actual shouldBe expected
        }

    @Test
    fun `내 타입만 선택된 경우, 내 타입에 대한 모든 상성 결과를 불러온다`() =
        runTest {
            // given
            val selectedMyType = TypeUiModel.FAIRY

            // when
            viewModel.selectType(SelectorType.MINE, selectedMyType)

            // then
            launch {
                viewModel.type.collect { actual ->
                    val expected =
                        FakeTypeRepository.DUMMY_RESULTS_AGAINST_MINE.toUi(
                            Type.FAIRY.id,
                            isMyType = true,
                        ).sortedWith(MatchedTypesUiModelComparator)
                    actual shouldBe expected
                    cancel()
                }
            }
        }

    @Test
    fun `상대 타입 1개만 선택하는 경우, 해당 타입에 대한 모든 상성 결과를 불러온다`() =
        runTest {
            // given
            val selectedType = TypeUiModel.FAIRY

            // when
            viewModel.selectType(SelectorType.OPPONENT1, selectedType)

            // then
            launch {
                viewModel.type.collect { actual ->
                    val expected =
                        FakeTypeRepository.DUMMY_RESULTS_AGAINST_OPPONENT.toUi(
                            Type.FAIRY.id,
                            isMyType = false,
                        ).sortedWith(MatchedTypesUiModelComparator)
                    actual shouldBe expected
                    cancel()
                }
            }
        }

    @Test
    fun `내 타입과 상대 타입 모두를 선택하면, 두 타입의 상성 결과를 불러온다`() =
        runTest {
            // given
            val myType = TypeUiModel.FAIRY
            val opponentType = TypeUiModel.FIGHTING

            // when
            viewModel.selectType(SelectorType.MINE, myType)
            viewModel.selectType(SelectorType.OPPONENT1, opponentType)

            // then
            launch {
                viewModel.type.collect { actual ->
                    val expected =
                        FakeTypeRepository.DUMMY_RESULTS.toUi(Type.FAIRY.id, isMyType = true)
                            .sortedWith(MatchedTypesUiModelComparator)
                    actual shouldBe expected
                    cancel()
                }
            }
        }
}
