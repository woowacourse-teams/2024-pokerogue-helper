package poke.rogue.helper.data.model


enum class PokemonSort : Comparator<Pokemon> {
    ByBaseStat {
        override fun compare(p1: Pokemon, p2: Pokemon): Int {
            return p1.baseStat.compareTo(p2.baseStat)
        }
    },
    ByDexNumber {
        override fun compare(p1: Pokemon, p2: Pokemon): Int {
            return p1.dexNumber.compareTo(p2.dexNumber)
        }
    },
    BySpeed {
        override fun compare(p1: Pokemon, p2: Pokemon): Int {
            return p1.hp.compareTo(p2.hp)
        }
    },
    ByAttack {
        override fun compare(p1: Pokemon, p2: Pokemon): Int {
            return p1.speed.compareTo(p2.speed)
        }
    },
    ByDefense {
        override fun compare(p1: Pokemon, p2: Pokemon): Int {
            return p1.defense.compareTo(p2.defense)
        }
    },
    BySpecialAttack {
        override fun compare(p1: Pokemon, p2: Pokemon): Int {
            return p1.specialAttack.compareTo(p2.specialAttack)
        }
    },
    BySpecialDefense {
        override fun compare(p1: Pokemon, p2: Pokemon): Int {
            return p1.specialDefense.compareTo(p2.specialDefense)
        }
    },
    ByHp {
        override fun compare(p1: Pokemon, p2: Pokemon): Int {
            return p1.hp.compareTo(p2.hp)
        }
    }
}
