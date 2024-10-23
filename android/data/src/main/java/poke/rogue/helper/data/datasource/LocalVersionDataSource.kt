package poke.rogue.helper.data.datasource

import poke.rogue.helper.local.datastore.VersionDataStore

class LocalVersionDataSource(
    private val versionDataStore: VersionDataStore,
) {
    fun databaseVersionStream() = versionDataStore.databaseVersionStream()

    suspend fun saveDatabaseVersion(version: Int) = versionDataStore.saveDatabaseVersion(version)
}
