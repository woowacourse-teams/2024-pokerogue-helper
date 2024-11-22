package poke.rogue.helper.testing.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.repository.TypeRepository
import poke.rogue.helper.testing.di.testingModule

class FakeTypeRepositoryTest : KoinTest {
    private val repository: TypeRepository
        get() = get()

    @JvmField
    @RegisterExtension
    val koinExtension =
        KoinTestExtension.create {
            modules(testingModule)
        }

    @Test
    fun `유효하지 않은 내 Type ID값으로 조회하면, 예외가 발생한다`() =
        runTest {
            assertThrows<IllegalArgumentException> {
                repository.matchedTypesAgainstMyType(-1)
            }
        }

    @Test
    fun `유효하지 않은 상대 Type ID값으로 조회하면, 예외가 발생한다`() =
        runTest {
            assertThrows<IllegalArgumentException> {
                repository.matchedTypesAgainstOpponent(-1)
            }
        }

    @Test
    fun `유효하지 않은 Type ID값이 하나라도 포함될 경우, 예외가 발생한다`() =
        runTest {
            assertThrows<IllegalArgumentException> {
                repository.matchedTypes(Type.FAIRY.id, listOf(-1))
            }
        }
}
