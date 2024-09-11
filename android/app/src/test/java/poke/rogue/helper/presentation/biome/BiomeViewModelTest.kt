package poke.rogue.helper.presentation.biome

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import poke.rogue.helper.data.repository.BiomeRepository
import poke.rogue.helper.testing.CoroutinesTestExtension
import poke.rogue.helper.testing.data.repository.FakeBiomeRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class BiomeViewModelTest {
    private lateinit var repository: BiomeRepository
    private lateinit var viewModel: BiomeViewModel

    @BeforeEach
    fun setUp() {
        repository = FakeBiomeRepository()
    }

    @Test
    fun `뷰모델이 생성될 때,모든 바이옴 정보를 불러온다`() =
        runTest {
            // given,when
            viewModel = BiomeViewModel(repository)
            val biomes = viewModel.biome.first { it is BiomeUiState.Success }
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
            viewModel = BiomeViewModel(repository)
            val biomeId = "grass"

            // when
            viewModel.navigateToDetail(biomeId)
            val actualId = viewModel.navigationToDetailEvent.first()

            // then
            actualId shouldBe biomeId
        }
}
