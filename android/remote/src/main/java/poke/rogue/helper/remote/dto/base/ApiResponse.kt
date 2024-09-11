package poke.rogue.helper.remote.dto.base

import poke.rogue.helper.remote.dto.base.ApiResponse.Failure
import poke.rogue.helper.remote.dto.base.ApiResponse.Success

/**
 * NetworkState is a sealed class that represents the state of a network request.
 * It can be one of the following:
 * - [Success] : 200..300 코드 사이의 응답
 * - [Failure] : 200 번대 외의 응답
 * - [HttpException] : 네트워크 에러 (서버와 관련된 에러, 404, 401...)
 * - [NetworkException] : 알 수 없는 에러 (IOException, ConnectException, SocketTimeoutException 이외의 에러)
 */
sealed interface ApiResponse<out T> {
    data class Success<T : Any>(val data: T) : ApiResponse<T>

    sealed class Failure(val throwable: Throwable) : ApiResponse<Nothing> {
        data class HttpException(val code: Int, private val error: Throwable) : Failure(error)

        data class NetworkException(private val error: Throwable) : Failure(error)

        data class UnknownError(private val error: Throwable) : Failure(error)
    }
}
