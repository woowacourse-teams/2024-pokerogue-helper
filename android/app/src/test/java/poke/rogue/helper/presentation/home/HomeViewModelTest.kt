package poke.rogue.helper.presentation.home

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
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import poke.rogue.helper.presentation.di.testViewModelModule
import poke.rogue.helper.testing.CoroutinesTestExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class HomeViewModelTest : KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension =
        KoinTestExtension.create {
            modules(testViewModelModule)
        }

    private val viewModel: HomeViewModel
        get() = get<HomeViewModel>()

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `포켓몬 도감 화면으로 이동한다`() =
        runTest {
            viewModel.navigateToDex()

            val event = viewModel.navigationEvent.first()
            event shouldBe HomeNavigateEvent.ToDex
        }

    @Test
    fun `포켓몬 배틀 화면으로 이동한다`() =
        runTest {
            viewModel.navigateToBattle()

            val event = viewModel.navigationEvent.first()
            event shouldBe HomeNavigateEvent.ToBattle
        }

    @Test
    fun `특성 화면으로 이동한다`() =
        runTest {
            viewModel.navigateToAbility()

            val event = viewModel.navigationEvent.first()
            event shouldBe HomeNavigateEvent.ToAbility
        }

    @Test
    fun `포켓로그 화면으로 이동한다`() =
        runTest {
            viewModel.navigateToPokeRogue()

            val event = viewModel.navigationEvent.first()
            event shouldBe HomeNavigateEvent.ToLogo
        }

    @Test
    fun `바이옴 화면으로 이동한다`() =
        runTest {
            viewModel.navigateToBiome()

            val event = viewModel.navigationEvent.first()
            event shouldBe HomeNavigateEvent.ToBiome
        }
}
