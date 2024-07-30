package poke.rogue.helper.presentation.type

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import poke.rogue.helper.data.model.MatchedResult
import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.repository.TypeRepository
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi
import poke.rogue.helper.utils.CoroutinesTestExtension

@ExperimentalCoroutinesApi
@ExtendWith(CoroutinesTestExtension::class)
@ExtendWith(MockKExtension::class)
class TypeViewModelTest {
    @RelaxedMockK
    private lateinit var typeRepository: TypeRepository

    private lateinit var viewModel: TypeViewModel

    @BeforeEach
    fun setup() {
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
            // when
            viewModel.selectType(SelectorType.MINE, TypeUiModel.FAIRY)

            // then
            val expected = TypeSelectionUiState.Selected(TypeUiModel.FAIRY)
            val actual = viewModel.typeSelectionStates.value.myType
            actual shouldBe expected
        }

    @Test
    fun `내 타입만 선택된 경우, 내 타입에 대한 모든 상성 결과를 불러온다`() =
        runTest {
            // given
            val selectedType = TypeUiModel.FAIRY
            coEvery { typeRepository.matchedTypesAgainstMyType(selectedType.id) } returns FakeMatchedResults

            // when
            viewModel.selectType(SelectorType.MINE, selectedType)

            // then
            launch {
                viewModel.type.collect { actual ->
                    val expected = FakeMatchedResults.toUi(Type.FAIRY.id, isMyType = true)
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
            coEvery { typeRepository.matchedTypesAgainstOpponent(selectedType.id) } returns FakeMatchedResults

            // when
            viewModel.selectType(SelectorType.OPPONENT1, selectedType)

            // then
            launch {
                viewModel.type.collect { actual ->
                    val expected = FakeMatchedResults.toUi(Type.FAIRY.id, isMyType = false)
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
            coEvery { typeRepository.matchedTypes(myType.id, listOf(opponentType.id)) } returns FakeMatchedResults

            // when
            viewModel.selectType(SelectorType.MINE, myType)
            viewModel.selectType(SelectorType.OPPONENT1, opponentType)

            // then
            launch {
                viewModel.type.collect { actual ->
                    val expected =
                        FakeMatchedResults.toUi(Type.FAIRY.id, isMyType = true)
                    actual shouldBe expected
                    cancel()
                }
            }
        }
}

private val FakeMatchedResults = listOf(MatchedTypes(MatchedResult.STRONG, listOf(Type.FIGHTING)))
