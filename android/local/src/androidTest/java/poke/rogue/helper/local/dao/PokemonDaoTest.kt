package poke.rogue.helper.local.dao

import androidx.room.Room
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import poke.rogue.helper.local.db.PokeRogueDatabase
import poke.rogue.helper.local.entity.pokemonEntity
import poke.rogue.helper.local.utils.testContext

class RecentProductDaoTest {
    private lateinit var dao: PokemonDao

    @Before
    fun setUp() {
        val db =
            Room.inMemoryDatabaseBuilder(
                testContext,
                PokeRogueDatabase::class.java,
            ).build()
        dao = db.pokemonDao()
    }

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
            println(actual)
            // then
            val expect = pokemons
            actual shouldBe expect
        }
}
