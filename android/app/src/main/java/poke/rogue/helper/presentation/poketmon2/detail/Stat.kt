package poke.rogue.helper.presentation.poketmon2.detail

data class Stat(
    val name: String,
    val amount: Int,
    val limit: Int,
) {
    val progress: Int
        get() = amount * 100 / limit
}