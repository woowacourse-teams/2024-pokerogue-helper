package poke.rogue.helper.data.datasource

import android.content.Context
import kotlinx.coroutines.flow.Flow
import poke.rogue.helper.local.datastore.NavigationModeDataStore

class LocalNavigationDataSource(
    private val dataStore: NavigationModeDataStore,
) {
    suspend fun saveNavigationMode(isBattleNavigationMode: Boolean) {
        dataStore.saveNavigationMode(isBattleNavigationMode)
    }

    fun isBattleNavigationModeStream(): Flow<Boolean> = dataStore.isBattleNavigationMode()

    companion object {
        private var instance: LocalNavigationDataSource? = null

        fun instance(context: Context): LocalNavigationDataSource =
            instance ?: LocalNavigationDataSource(
                NavigationModeDataStore(context),
            ).also {
                instance = it
            }
    }
}
