package poke.rogue.helper.data.model

@JvmInline
value class Type private constructor(val name: String) {
    companion object {
        private val TYPES: MutableMap<String, Type> =
            mutableMapOf(
                "normal" to Type("normal"),
                "fire" to Type("fire"),
                "water" to Type("water"),
                "electric" to Type("electric"),
                "grass" to Type("grass"),
                "ice" to Type("ice"),
                "fighting" to Type("fighting"),
                "poison" to Type("poison"),
                "ground" to Type("ground"),
                "flying" to Type("flying"),
                "psychic" to Type("psychic"),
                "bug" to Type("bug"),
                "rock" to Type("rock"),
                "ghost" to Type("ghost"),
                "dragon" to Type("dragon"),
                "dark" to Type("dark"),
                "steel" to Type("steel"),
                "fairy" to Type("fairy"),
            )

        fun of(name: String): Type = TYPES.getOrPut(name) { Type(name) }
    }
}
