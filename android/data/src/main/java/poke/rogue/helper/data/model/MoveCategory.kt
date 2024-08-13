package poke.rogue.helper.data.model

data class MoveCategory(
    val id: Long,
    val name: String,
    val icon: String,
) {
    companion object {
        val physicalMove =
            MoveCategory(
                id = 1,
                name = "물리",
                icon = "https://ydarissep.github.io/PokeRogue-Pokedex/src/moves/SPLIT_PHYSICAL.png",
            )

        val specialMove =
            MoveCategory(
                id = 2,
                name = "특수",
                icon = "https://ydarissep.github.io/PokeRogue-Pokedex/src/moves/SPLIT_SPECIAL.png",
            )

        val statusMove =
            MoveCategory(
                id = 3,
                name = "변화",
                icon = "https://ydarissep.github.io/PokeRogue-Pokedex/src/moves/SPLIT_STATUS.png",
            )
    }
}
