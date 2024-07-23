package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.Ability

interface AbilityRepository {
    fun abilities(): List<Ability>
}
