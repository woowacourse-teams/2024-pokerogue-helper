package poke.rogue.helper.remote.dto.response.version

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionResponse(
    @SerialName("currentVersion")
    val version: Int,
)
