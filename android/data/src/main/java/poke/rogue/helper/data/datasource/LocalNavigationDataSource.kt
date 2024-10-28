package poke.rogue.helper.data.datasource

import kotlinx.coroutines.flow.Flow
import poke.rogue.helper.local.datastore.NavigationModeDataStore

class LocalNavigationDataSource(
    private val dataStore: NavigationModeDataStore,
) {
    suspend fun saveNavigationMode(isBattleNavigationMode: Boolean) {
        dataStore.saveNavigationMode(isBattleNavigationMode)
    }

    fun isBattleNavigationModeStream(): Flow<Boolean> = dataStore.isBattleNavigationMode()
}
