package poke.rogue.helper.testing.data.repository

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import poke.rogue.helper.data.model.PokemonFilter
import poke.rogue.helper.data.model.PokemonGeneration
import poke.rogue.helper.data.model.PokemonSort
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.stringmatcher.has

class FakeDexRepositoryTest {
    private lateinit var repository: FakeDexRepository

    @BeforeEach
    fun setUp() {
        repository = FakeDexRepository()
    }

    @Test
    fun `기본적으로 도감 번호순, 모든 세대의 포켓몬을 가져온다`() =
        runTest {
            // given
            val sort = PokemonSort.BySpeed
            // when
            val actual = repository.filteredPokemons()
            // then
            val expect = repository.pokemons()
            actual shouldBe expect
        }

    @Test
    fun `불 타입을 가지고 있는 포켓몬을 가져온다`() =
        runTest {
            // given
            val type = Type.FIRE
            // when
            val actual = repository.filteredPokemons(filters = listOf(PokemonFilter.ByType(type)))
            // then
            val expect = repository.pokemons().filter { it.types.contains(type) }
            actual shouldBe expect
            actual shouldNotBe repository.pokemons()
        }

    @Test
    fun `3 세대에 해당하는 포켓몬을 가져온다`() =
        runTest {
            // given
            val generation = PokemonGeneration.of(3)
            // when
            val actual =
                repository.filteredPokemons(filters = listOf(PokemonFilter.ByGeneration(generation)))
            // then
            val expect = repository.pokemons().filter { it.generation == generation }
            actual shouldBe expect
            actual shouldNotBe repository.pokemons()
        }

    @Test
    fun `Speed 수치가 높은 순서대로 정렬된 포켓몬을 가져온다`() =
        runTest {
            // given
            val sort = PokemonSort.BySpeed
            // when
            val actual =
                repository.filteredPokemons(
                    sort = sort,
                )
            // then
            val expect =
                repository.pokemons()
                    .sortedByDescending { it.speed }
            actual shouldBe expect
            actual shouldNotBe repository.pokemons()
        }

    @Test
    fun `ㄹㅈ 초성에 해당하고 1세대에 해당하는 포켓몬을 가져온다`() =
        runTest {
            // given
            val name = "ㄹㅈ"
            val generation = PokemonGeneration.of(1)
            // when
            val actual =
                repository.filteredPokemons(
                    name = name,
                    filters =
                        listOf(
                            PokemonFilter.ByGeneration(generation),
                        ),
                )
            // then
            val expect =
                repository.pokemons()
                    .filter { it.name.has("ㄹㅈ") }
                    .filter { it.generation == generation }
            actual shouldBe expect
            actual shouldNotBe repository.pokemons()
        }

    @Test
    fun `불 속성 타입과 1세대에 해당하는 포켓몬을 가져온다`() =
        runTest {
            // given
            val generation = PokemonGeneration.of(1)
            // when
            val actual =
                repository.filteredPokemons(
                    filters =
                        listOf(
                            PokemonFilter.ByGeneration(generation),
                            PokemonFilter.ByType(Type.FIRE),
                        ),
                )
            // then
            val expect =
                repository.pokemons()
                    .filter { it.generation == generation }
                    .filter { it.types.contains(Type.FIRE) }
            actual shouldBe expect
            actual shouldNotBe repository.pokemons()
        }
}
