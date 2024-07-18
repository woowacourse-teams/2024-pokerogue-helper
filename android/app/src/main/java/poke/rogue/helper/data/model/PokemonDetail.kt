package poke.rogue.helper.data.model

data class PokemonDetail(
    val pokemon: Pokemon,
    val stats: List<Stat>,
    val abilities: List<String>,
    val height: Float,
    val weight: Float,
) {
    companion object {
        val DUMMY =
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
