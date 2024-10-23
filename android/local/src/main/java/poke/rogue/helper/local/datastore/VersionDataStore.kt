package poke.rogue.helper.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VersionDataStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_BASE_NAME)

    fun databaseVersionStream(): Flow<Int?> =
        context.dataStore.data.map { preferences ->
            preferences[DATABASE_VERSION_KEY]
        }

    suspend fun saveDatabaseVersion(version: Int) {
        context.dataStore.edit { preferences ->
            preferences[DATABASE_VERSION_KEY] = version
        }
    }

    companion object {
        private const val DATA_BASE_NAME = "datastore_version"
        private val DATABASE_VERSION_KEY = intPreferencesKey("database_version")
    }
}
