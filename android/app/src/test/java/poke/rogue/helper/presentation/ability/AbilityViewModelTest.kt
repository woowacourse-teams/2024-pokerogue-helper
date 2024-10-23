package poke.rogue.helper.presentation.ability

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import poke.rogue.helper.presentation.ability.model.AbilityUiModel
import poke.rogue.helper.presentation.ability.model.toUi
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.CoroutinesTestExtension
import poke.rogue.helper.testing.data.repository.FakeAbilityRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class AbilityViewModelTest : KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension =
        KoinTestExtension.create {
            modules(testViewModelModule)
        }
    private val viewModel: AbilityViewModel
        get() = get<AbilityViewModel>()

    @Test
    fun `모든 특성을 불러온다`() =
        runTest {
            // when
            val uiState = viewModel.uiState.first { it is AbilityUiState.Success }

            // then
            val abilities = (uiState as AbilityUiState.Success<List<AbilityUiModel>>).data
            abilities shouldBe FakeAbilityRepository().abilities().map { it.toUi() }
        }

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
