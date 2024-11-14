package poke.rogue.helper.data.datasource

import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.data.exception.getOrThrow
import poke.rogue.helper.data.exception.onFailure
import poke.rogue.helper.remote.service.VersionService

class RemoteVersionDataSource(
    private val versionService: VersionService,
    private val logger: AnalyticsLogger,
) {
    suspend fun databaseVersion(): Int =
        versionService.databaseVersion()
            .onFailure {
                logger.logError(throwable, "RemoteVersionDataSource - databaseVersion() 에서 발생")
            }.getOrThrow().version
}
