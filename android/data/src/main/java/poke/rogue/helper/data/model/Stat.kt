package poke.rogue.helper.data.model

data class Stat(
    val name: String,
    val amount: Int,
) {
    companion object {
        val DUMMY_STATS =
            listOf(
                Stat(
                    name = "hp",
                    amount = 45,
                ),
                Stat(
                    name = "attack",
                    amount = 49,
                ),
                Stat(
                    name = "defense",
                    amount = 49,
                ),
                Stat(
                    name = "specialAttack",
                    amount = 65,
                ),
                Stat(
                    name = "specialDefense",
                    amount = 65,
                ),
                Stat(
                    name = "speed",
                    amount = 45,
                ),
                Stat(
                    name = "total",
                    amount = 318,
                ),
            )
    }
}
