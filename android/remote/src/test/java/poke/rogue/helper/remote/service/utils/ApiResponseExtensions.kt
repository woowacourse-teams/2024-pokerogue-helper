package poke.rogue.helper.remote.service.utils

import poke.rogue.helper.remote.dto.base.ApiResponse

fun ApiResponse<Any>.getOrThrow(): Any {
    return when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Failure -> throw throwable
    }
}