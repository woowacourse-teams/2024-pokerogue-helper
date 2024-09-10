package poke.rogue.helper.data.model.fixture

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

/**
 * Test fixture for [PokemonResponse].
 * @param id 포켓몬의 id. default: `1`
 * @param pokedexNumber 포켓몬의 도감 번호. default: `id * 10`
 * @param name 포켓몬의 이름. default: `pokemon$id`
 * @param image 포켓몬의 이미지. default: `logo$id`
 * @param types 포켓몬의 타입 목록. default: 풀, 독
 * @return [PokemonResponse]
 */
fun pokemonResponse(
    id: Long = 1,
    pokedexNumber: Long = id * 10,
    name: String = "pokemon$id",
    image: String = "logo$id",
    types: List<PokemonTypeResponse> = pokemonTypeResponses("grass", "poison"),
) = PokemonResponse(
    id = id,
    pokedexNumber = pokedexNumber,
    name = name,
    image = image,
    types = types,
)

/**
 * Test fixture for [List]<[PokemonResponse]>.
 * @param ids 포켓몬의 id 목록 (vararg)
 * @return [List]<[PokemonResponse]>
 *
 * also see [pokemonResponse]
 */
fun pokemonResponses(vararg ids: Long): List<PokemonResponse> = ids.map(::pokemonResponse)

/**
 * Test fixture for [Pokemon].
 * @param id 포켓몬의 id. default: `1`
 * @param dexNumber 포켓몬의 도감 번호. default: `id * 10`
 * @param name 포켓몬의 이름. default: `pokemon$id`
 * @param imageUrl 포켓몬의 이미지. default: `logo$id`
 * @param types 포켓몬의 타입 목록. default: 풀, 독
 * @return [Pokemon]
 */
fun pokemon(id: String) =
    Pokemon(
        id = id,
        dexNumber = id.toLong() * 10,
        name = "pokemon$id",
        imageUrl = "logo$id",
        backImageUrl = "",
        types = listOf(Type.GRASS, Type.POISON),
    )

/**
 * Test fixture for [List]<[Pokemon]>.
 * @param ids 포켓몬의 id 목록 (vararg)
 * @return [List]<[Pokemon]>
 */
fun pokemons(vararg ids: String) = ids.map(::pokemon)
