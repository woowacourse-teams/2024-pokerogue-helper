package poke.rogue.helper.testing.data.repository

import poke.rogue.helper.data.model.Ability
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.Stat
import poke.rogue.helper.data.repository.PokemonDetailRepository

class FakePokemonDetailRepository : PokemonDetailRepository {
    override suspend fun pokemonDetail(id: Long): PokemonDetail = DUMMY_POKEMON_DETAIL

    companion object {
        val DUMMY_POKEMON_DETAIL =
            PokemonDetail(
                pokemon = Pokemon.DUMMY,
                stats =
                    listOf(
                        Stat("hp", 45),
                        Stat("attack", 49),
                        Stat("defense", 49),
                        Stat("specialAttack", 65),
                        Stat("specialDefense", 65),
                        Stat("speed", 45),
                        Stat("total", 318),
                    ),
                abilities =
                    listOf(
                        Ability(450, "심록", description = "HP가 줄었을 때 풀타입 기술의 위력이 올라간다."),
                        Ability(419, "엽록소", description = "날씨가 맑을 때 스피드가 올라간다."),
                    ),
                height = 0.7f,
                weight = 6.9f,
            )
    }
}
