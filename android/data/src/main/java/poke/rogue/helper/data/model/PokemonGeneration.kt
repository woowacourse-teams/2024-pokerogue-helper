package poke.rogue.helper.data.model


enum class PokemonGeneration(val number: Int) {
    ALL(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    companion object {
        fun of(number: Int): PokemonGeneration {
            return entries.find { it.number == number }
                ?: throw IllegalArgumentException("Unknown generation number: $number")
        }
    }
}