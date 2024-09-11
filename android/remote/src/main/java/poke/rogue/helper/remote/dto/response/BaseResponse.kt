package poke.rogue.helper.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val message: String,
    val data: T,
)
