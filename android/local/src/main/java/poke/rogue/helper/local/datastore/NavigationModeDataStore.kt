package poke.rogue.helper.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class NavigationModeDataStore(private val context: Context) {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = NAVIGATION_MODE_PREFERENCE_NAME)

    suspend fun saveNavigationMode(isBattleNavigationMode: Boolean) {
        context.datastore.edit {
            it[IS_BATTLE_NAVIGATION_MODE_KEY] = isBattleNavigationMode
        }
    }

    fun isBattleNavigationMode(): Flow<Boolean> = context.datastore.data.mapNotNull { it[IS_BATTLE_NAVIGATION_MODE_KEY] }

    private companion object {
        const val NAVIGATION_MODE_PREFERENCE_NAME = "navigationMode"
        val IS_BATTLE_NAVIGATION_MODE_KEY = booleanPreferencesKey("is_battle_navigation_mode")
    }
}
