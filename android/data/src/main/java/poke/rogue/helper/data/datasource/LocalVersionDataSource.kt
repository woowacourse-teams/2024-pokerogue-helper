package poke.rogue.helper.data.datasource

import poke.rogue.helper.local.datastore.VersionDataStore

class LocalVersionDataSource(
    private val versionDataStore: VersionDataStore,
) {
    fun databaseVersion() = versionDataStore.databaseVersion()

    suspend fun saveDatabaseVersion(version: Int) = versionDataStore.saveDatabaseVersion(version)
}
