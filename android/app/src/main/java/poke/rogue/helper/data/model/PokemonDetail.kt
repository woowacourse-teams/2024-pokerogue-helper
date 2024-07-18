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
                        Stat("attack", 49),
                        Stat("defense", 49),
                        Stat("specialAttack", 65),
                        Stat("specialDefense", 65),
                        Stat("speed", 45),
                        Stat("total", 318),
                    ),
                abilities = listOf("그래스메이커", "심록", "엽록소"),
                height = 0.7f,
                weight = 6.9f,
            )
    }
}
