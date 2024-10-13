package poke.rogue.helper.testing.data.repository

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import poke.rogue.helper.data.repository.AbilityRepository
import poke.rogue.helper.testing.di.testingModule

class FakeAbilityRepositoryTest : KoinTest {
    private val repository: AbilityRepository
        get() = get()

    @JvmField
    @RegisterExtension
    val koinExtension = KoinTestExtension.create {
        modules(testingModule)
        logger(PrintLogger(Level.DEBUG))
    }

    @Test
    fun `모든 특성 정보를 불러온다`() =
        runTest {
            // when
            val actualAbilities = repository.abilities()

            // then
            actualAbilities.size shouldBe 24
        }

    @Test
    fun `잘못된 특성 이름을 검색했을 때, 빈 리스트를 반환한다`() =
        runTest {
            // given
            val query = "잘못된 특성 이름"

            // when
            val ability = repository.abilities(query)

            // then
            ability.isEmpty() shouldBe true
        }

    @Test
    fun `올바른 특성의 이름을 검색했을 때, 해당하는 특성을 반환한다`() =
        runTest {
            // when
            val ability = repository.abilities("타오르는불꽃")

            // then
            val actual = ability.find { it.title == "타오르는불꽃" }?.title
            val expect = "타오르는불꽃"
            actual shouldBe expect
        }

    @Test
    fun `특성의 이름을 초성으로 검색했을 때, 해당하는 특성을 반환한다`() =
        runTest {
            // given
            val query = "ㅌㅇㄹㄴㅂㄲ"

            // when
            val ability = repository.abilities(query)

            // then
            ability.find { it.title == "타오르는불꽃" }?.title shouldBe "타오르는불꽃"
        }

    @Test
    fun `잘못된 ID값으로 조회하면, 특성 정보를 불러올 때 예외가 발생한다`() =
        runTest {
            // when, then
            shouldThrow<IllegalArgumentException> {
                repository.abilityDetail("-1")
            }
        }
}
