package poke.rogue.helper.presentation.ability

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import poke.rogue.helper.data.repository.AbilityRepository
import poke.rogue.helper.presentation.ability.model.AbilityUiModel
import poke.rogue.helper.presentation.ability.model.toUi
import poke.rogue.helper.testing.CoroutinesTestExtension
import poke.rogue.helper.testing.data.repository.FakeAbilityRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class AbilityViewModelTest {
    private lateinit var repository: AbilityRepository
    private lateinit var viewModel: AbilityViewModel

    @BeforeEach
    fun setUp() {
        repository = FakeAbilityRepository()
        viewModel = AbilityViewModel(repository)
    }

    @Test
    fun `모든 특성을 불러온다`() =
        runTest {
            // when
            val uiState = viewModel.uiState.first { it is AbilityUiState.Success }

            // then
            val abilities = (uiState as AbilityUiState.Success<List<AbilityUiModel>>).data
            abilities shouldBe repository.abilities().map { it.toUi() }
        }

//    @Test
//    fun `특성 이름으로 검색한다`() =
//        runTest {
//            // given
//            val query = "불"
//
//            // when
//            val queriedAbilities = viewModel.uiState.first { it is AbilityUiState.Success }
//            viewModel.queryName(query)
//
//            // then
//            val abilities = (queriedAbilities as AbilityUiState.Success<List<AbilityUiModel>>).data
//            val actual = abilities.map { it.id }
//            actual shouldBe listOf("14", "17", "24")
//        }

    @Test
    fun `특성 id값으로 특성 상세 화면으로 이동한다`() =
        runTest {
            // given
            val abilityId = "15L"

            // when
            launch {
                viewModel.navigateToDetail(abilityId)
            }

            // then
            val actualId = viewModel.navigationToDetailEvent.first()
            actualId shouldBe abilityId
        }
}
