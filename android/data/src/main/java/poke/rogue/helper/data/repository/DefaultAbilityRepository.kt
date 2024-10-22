package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemoteAbilityDataSource
import poke.rogue.helper.data.model.Ability
import poke.rogue.helper.data.model.AbilityDetail
import poke.rogue.helper.stringmatcher.has

class DefaultAbilityRepository(private val remoteAbilityDataSource: RemoteAbilityDataSource) :
    AbilityRepository {
    private var cachedAbilities: List<Ability> = emptyList()

    override suspend fun abilities(): List<Ability> {
        if (cachedAbilities.isEmpty()) {
            cachedAbilities = remoteAbilityDataSource.abilities()
        }
        return cachedAbilities
    }

    override suspend fun abilities(query: String): List<Ability> {
        if (query.isBlank()) {
            return abilities()
        }
        return abilities().filter { it.title.has(query) }
    }

    override suspend fun abilityDetail(id: String): AbilityDetail = remoteAbilityDataSource.abilityDetail(id)

    companion object {
        private var instance: AbilityRepository? = null

        fun instance(): AbilityRepository {
            return instance ?: DefaultAbilityRepository(RemoteAbilityDataSource.instance()).also {
                instance = it
            }
        }
    }
}
