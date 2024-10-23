package poke.rogue.helper.presentation.ability.detail

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import poke.rogue.helper.presentation.ability.model.toUi
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.CoroutinesTestExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class AbilityDetailViewModelTest : KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension =
        KoinTestExtension.create {
            modules(testViewModelModule)
        }
    private val viewModel: AbilityDetailViewModel
        get() = get<AbilityDetailViewModel>()

    @Test
    fun `특성 id값으로 특성 상세 정보를 불러온다`() =
        runTest {
            // given
            val abilityId = "1L"

            // when
            viewModel.updateAbilityDetail(abilityId)
            val abilityDetail =
                viewModel.abilityDetail.first {
                    it is AbilityDetailUiState.Success
                }
            val detail = (abilityDetail as AbilityDetailUiState.Success).data.toUi()

            // then
            detail.title shouldBe "악취"
            detail.description shouldBe "악취를 풍겨서 공격했을 때 상대가 풀죽을 때가 있다."
        }

    @Test
    fun `포켓몬 id 값으로 포켓몬 상세 화면으로 이동한다`() =
        runTest {
            // given
            Dispatchers.setMain(StandardTestDispatcher())
            val pokemonId = "1L"

            // when
            viewModel.navigateToPokemonDetail(pokemonId)

            // then
            val actualId = viewModel.navigationToPokemonDetailEvent.first()
            actualId shouldBe pokemonId
        }
}
