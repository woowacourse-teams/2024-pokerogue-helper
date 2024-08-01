package poke.rogue.helper.testing.data.repository

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import poke.rogue.helper.data.repository.AbilityRepository

class FakeAbilityRepositoryTest {

    private lateinit var repository: AbilityRepository

    @BeforeEach
    fun setUp() {
        repository = FakeAbilityRepository()
    }

    @Test
    fun `모든 특성 정보를 불러온다`() = runTest {
        // given
        val expectedAbilities = FakeAbilityRepository.ABILITES

        // when
        val actualAbilities = repository.abilities()

        // then
        actualAbilities shouldBe expectedAbilities
    }

    @Test
    fun `잘못된 특성 이름을 검색했을 때, 빈 리스트를 반환한다`() = runTest {
        // given
        val query = "잘못된 특성 이름"

        // when
        val ability = repository.abilities(query)

        // then
        ability.isEmpty() shouldBe true
    }

    @Test
    fun `올바른 특성의 이름을 검색했을 때, 해당하는 특성을 반환한다`() = runTest {
        // given
        val query = "타오르는불꽃"

        // when
        val ability = repository.abilities(query)

        // then
        ability.find { it.title == query }?.title shouldBe query
    }

    @Test
    fun `특성의 이름을 초성으로 검색했을 때, 해당하는 특성을 반환한다`() = runTest {
        // given
        val query = "ㅌㅇㄹㄴㅂㄲ"

        // when
        val ability = repository.abilities(query)

        // then
        ability.find { it.title == "타오르는불꽃" }?.title shouldBe "타오르는불꽃"
    }
}
