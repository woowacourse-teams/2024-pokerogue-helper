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
                        Stat("HP", 45),
                        Stat("공격", 49),
                        Stat("방어", 49),
                        Stat("특수공격", 65),
                        Stat("특수방어", 65),
                        Stat("스피드", 45),
                        Stat("총합", 318),
                    ),
                abilities = listOf("그래스메이커", "심록", "엽록소"),
                height = 0.7f,
                weight = 6.9f,
            )
    }
}
