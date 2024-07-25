package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.Stat

class FakePokemonDetailDataSource {
    fun pokemonDetail(id: Long): PokemonDetail = DUMMY_POKEMON_DETAIL

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
                        Stat("totalStats", 318),
                    ),
                abilities = listOf("그래스메이커", "심록", "엽록소"),
                height = 0.7f,
                weight = 6.9f,
            )
    }
}
