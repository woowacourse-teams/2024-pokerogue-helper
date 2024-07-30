package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemoteAbilityDataSource
import poke.rogue.helper.data.model.Ability
import poke.rogue.helper.data.model.AbilityDetail

class DefaultAbilityRepository(private val remoteAbilityDataSource: RemoteAbilityDataSource) :
    AbilityRepository {
    override suspend fun abilities(): List<Ability> = remoteAbilityDataSource.abilities()

    override suspend fun abilities(query: String): List<Ability> =
        remoteAbilityDataSource.abilities().filter { ability ->
            ability.title.contains(query, ignoreCase = true)
        }

    override suspend fun abilityDetail(id: Long): AbilityDetail = remoteAbilityDataSource.abilityDetail(id)
}
