package poke.rogue.helper.presentation.biome

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
import poke.rogue.helper.data.repository.BiomeRepository
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.CoroutinesTestExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class BiomeViewModelTest : KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension =
        KoinTestExtension.create {
            modules(testViewModelModule)
        }

    private val viewModel: BiomeViewModel
        get() = get<BiomeViewModel>()

    @Test
    fun `뷰모델이 생성될 때,모든 바이옴 정보를 불러온다`() =
        runTest {
            // given,when
            val repository = getKoin().get<BiomeRepository>()
            val biomes = viewModel.biomes.first { it is BiomeUiState.Success }
            val actualBiomes = (biomes as BiomeUiState.Success).data

            // then
            val expectBiomes = repository.biomes()
            actualBiomes shouldBe expectBiomes
        }

    @Test
    fun `바이옴 ID값으로 바이옴 상세 화면으로 이동한다`() =
        runTest {
            // given
            Dispatchers.setMain(StandardTestDispatcher())
            val biomeId = "grass"

            // when
            viewModel.navigateToDetail(biomeId)
            val actualId = viewModel.navigationToDetailEvent.first()

            // then
            actualId shouldBe biomeId
        }

    @Test
    fun `바이옴 가이드 화면으로 이동한다`() =
        runTest {
            // given
            Dispatchers.setMain(StandardTestDispatcher())

            // when
            viewModel.navigateToGuide()
            val actual = viewModel.navigateToGuideEvent.first()

            // then
            actual shouldBe Unit
        }

    @Test
    fun `올바른 바이옴 이름을 검색했을 때, 해당하는 바이옴을 반환한다`() =
        runTest {
            // given
            val repository = getKoin().get<BiomeRepository>()

            // when
            val biome = repository.biomes("악지")

            // then
            val actual = biome.find { it.name == "악지" }?.name
            val expect = "악지"
            actual shouldBe expect
        }

    @Test
    fun `잘못된 바이옴 이름을 검색했을 때, 빈 리스트를 반환한다`() =
        runTest {
            // given
            val repository = getKoin().get<BiomeRepository>()

            // when
            val biome = repository.biomes("잘못된 이름")

            // then
            biome.size shouldBe 0
        }
}
