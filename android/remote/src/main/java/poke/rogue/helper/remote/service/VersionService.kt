package poke.rogue.helper.remote.service

import poke.rogue.helper.remote.dto.base.ApiResponse
import poke.rogue.helper.remote.dto.response.version.VersionResponse
import retrofit2.http.GET

interface VersionService {
    @GET("api/v1/database/version")
    suspend fun databaseVersion(): ApiResponse<VersionResponse>
}
