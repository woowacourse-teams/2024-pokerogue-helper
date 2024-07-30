package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemoteAbilityDataSource
import poke.rogue.helper.data.model.Ability
import poke.rogue.helper.data.model.AbilityDetail
import poke.rogue.helper.stringmatcher.has

class DefaultAbilityRepository(private val remoteAbilityDataSource: RemoteAbilityDataSource) :
    AbilityRepository {
    override suspend fun abilities(): List<Ability> = remoteAbilityDataSource.abilities()

    override suspend fun abilities(query: String): List<Ability> =
        remoteAbilityDataSource.abilities().filter { ability ->
            ability.title.has(query)
        }

    override suspend fun abilityDetail(id: Long): AbilityDetail = remoteAbilityDataSource.abilityDetail(id)
}
