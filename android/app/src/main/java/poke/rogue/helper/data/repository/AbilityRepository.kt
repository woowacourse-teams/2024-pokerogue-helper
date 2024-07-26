package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.Ability

interface AbilityRepository {
    suspend fun abilities(): List<Ability>

    suspend fun abilities(query: String): List<Ability>
}
