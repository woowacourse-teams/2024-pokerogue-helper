package poke.rogue.helper.remote.dto.base

val ApiResponse<Any>.isSuccess: Boolean
    get() = this is ApiResponse.Success

val ApiResponse<Any>.isFailure: Boolean
    get() = this is ApiResponse.Failure

val ApiResponse<Any>.isHttpException: Boolean
    get() = this is ApiResponse.Failure.HttpException

val ApiResponse<Any>.isNetworkException: Boolean
    get() = this is ApiResponse.Failure.NetworkException

val ApiResponse<Any>.isUnKnownError: Boolean
    get() = this is ApiResponse.Failure.UnknownError

val ApiResponse<Any>.messageOrNull: String?
    get() =
        when (this) {
            is ApiResponse.Failure -> throwable.message
            is ApiResponse.Success -> null
        }
