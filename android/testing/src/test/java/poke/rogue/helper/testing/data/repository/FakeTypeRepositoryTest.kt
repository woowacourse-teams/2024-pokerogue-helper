package poke.rogue.helper.testing.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import poke.rogue.helper.data.model.Type

class FakeTypeRepositoryTest {
    private lateinit var repository: FakeTypeRepository

    @BeforeEach
    fun setUp() {
        repository = FakeTypeRepository()
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
