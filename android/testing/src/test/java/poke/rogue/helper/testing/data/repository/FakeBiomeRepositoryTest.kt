package poke.rogue.helper.testing.data.repository

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import poke.rogue.helper.data.repository.BiomeRepository
import poke.rogue.helper.testing.di.testingModule

class FakeBiomeRepositoryTest : KoinTest {
    private val repository: BiomeRepository
        get() = get()

    @JvmField
    @RegisterExtension
    val koinExtension =
        KoinTestExtension.create {
            modules(testingModule)
        }

    @Test
    fun `모든 바이옴 리스트의 정보를 불러오면, 사이즈가 4이다`() =
        runTest {
            // when
            val actualBiomes = repository.biomes()

            // then
            actualBiomes.size shouldBe 4
        }

    @Test
    fun `잘못된 바이옴 ID값으로 조회하면, 예외가 발생한다`() =
        runTest {
            // when,then
            assertThrows<IllegalArgumentException> {
                repository.biomeDetail("-1")
            }
        }

    @Test
    fun `올바른 바이옴 ID값으로 조회하면, 바이옴 상세 정보를 반환한다`() =
        runTest {
            // when
            val biomeDetail = repository.biomeDetail("grass")

            // then
            biomeDetail.id shouldBe "grass"
        }
}
