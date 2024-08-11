package poke.rogue.helper.data.exception

import poke.rogue.helper.remote.dto.base.ApiResponse
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


fun <T> ApiResponse<T>.getOrNull(): T? {
    return when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Failure -> null
    }
}

fun <T> ApiResponse<T>.getOrElse(defaultValue: T): T {
    return when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Failure -> defaultValue
    }
}

inline fun <T> ApiResponse<T>.getOrElse(defaultValue: () -> T): T {
    return when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Failure -> defaultValue()
    }
}

fun <T> ApiResponse<T>.getOrThrow(): T {
    return when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Failure -> {
            when (this) {
                is ApiResponse.Failure.HttpException -> throw HttpException(code, throwable)
                is ApiResponse.Failure.NetworkException -> throw NetworkException(throwable)
                is ApiResponse.Failure.UnknownError -> throw UnknownException(throwable)
            }
        }
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> ApiResponse<T>.onSuccess(crossinline onResult: ApiResponse.Success<T>.() -> Unit): ApiResponse<T> {
    contract { callsInPlace(onResult, InvocationKind.AT_MOST_ONCE) }
    if (this is ApiResponse.Success) {
        onResult(this)
    }
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> ApiResponse<T>.onFailure(crossinline onResult: ApiResponse.Failure.() -> Unit): ApiResponse<T> {
    contract { callsInPlace(onResult, InvocationKind.AT_MOST_ONCE) }
    if (this is ApiResponse.Failure) {
        onResult(this)
    }
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> ApiResponse<T>.onHttpException(crossinline onResult: ApiResponse.Failure.HttpException.() -> Unit): ApiResponse<T> {
    contract { callsInPlace(onResult, InvocationKind.AT_MOST_ONCE) }
    if (this is ApiResponse.Failure.HttpException) {
        onResult(this)
    }
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> ApiResponse<T>.onNetworkException(
    crossinline onResult: ApiResponse.Failure.NetworkException.() -> Unit,
): ApiResponse<T> {
    contract { callsInPlace(onResult, InvocationKind.AT_MOST_ONCE) }
    if (this is ApiResponse.Failure.NetworkException) {
        onResult(this)
    }
    return this
}
