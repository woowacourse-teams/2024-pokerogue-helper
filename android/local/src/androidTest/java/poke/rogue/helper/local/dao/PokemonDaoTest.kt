package poke.rogue.helper.local.dao

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.koin.test.KoinTest
import org.koin.test.get
import poke.rogue.helper.local.di.testLocalModule
import poke.rogue.helper.local.entity.PokemonEntity
import poke.rogue.helper.local.entity.pokemonEntity
import poke.rogue.helper.local.utils.KoinAndroidUnitTestRule

class PokemonDaoTest : KoinTest {
    private val dao get() = get<PokemonDao>()

    @get:Rule
    val koinTestRule =
        KoinAndroidUnitTestRule(
            testLocalModule,
        )

    @Test
    @DisplayName("포켓몬 저장 후 불러오기")
    fun `test`() =
        runTest {
            // given
            val pokemons =
                listOf(pokemonEntity(id = "1", name = "피카츄"), pokemonEntity(id = "2", name = "라이츄"))
            // when
            dao.savePokemons(pokemons)
            val actual = dao.pokemons()
            // then
            val expect = pokemons
            actual shouldBe expect
        }

    @Test
    @DisplayName("포켓몬 저장 후 저장 확인, 삭제 후 삭제 확인")
    fun `test2`() =
        runTest {
            // given
            val pokemons =
                listOf(pokemonEntity(id = "1", name = "피카츄"), pokemonEntity(id = "2", name = "라이츄"))
            // when
            dao.savePokemons(pokemons)
            val actual = dao.pokemons()
            // then
            val expect = pokemons
            actual shouldBe expect
            // when
            dao.clear()
            val actual2 = dao.pokemons()
            // then
            val expect2 = emptyList<PokemonEntity>()
            actual2 shouldBe expect2
        }
}
