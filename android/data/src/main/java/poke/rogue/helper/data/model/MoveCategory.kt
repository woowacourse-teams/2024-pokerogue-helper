package poke.rogue.helper.data.model

data class MoveCategory(
    val id: Long,
    val name: String,
) {
    companion object {
        val physicalMove =
            MoveCategory(
                id = 1,
                name = "물리",
            )

        val specialMove =
            MoveCategory(
                id = 2,
                name = "특수",
            )

        val statusMove =
            MoveCategory(
                id = 3,
                name = "변화",
            )
    }
}
