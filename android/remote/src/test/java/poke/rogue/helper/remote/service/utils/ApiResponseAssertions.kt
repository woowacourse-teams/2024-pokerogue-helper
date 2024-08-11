package poke.rogue.helper.remote.service.utils

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import poke.rogue.helper.remote.dto.base.ApiResponse
import poke.rogue.helper.remote.dto.base.isFailure
import poke.rogue.helper.remote.dto.base.isHttpException
import poke.rogue.helper.remote.dto.base.isNetworkException
import poke.rogue.helper.remote.dto.base.isSuccess
import poke.rogue.helper.remote.dto.base.isUnKnownError

fun ApiResponse<Any>.shouldBeSuccess() {
    this.isSuccess.shouldBeTrue()
}

fun ApiResponse<Any>.shouldBeFailure() {
    this.isFailure.shouldBeTrue()
}

fun ApiResponse<Any>.shouldBeHttpException() {
    this.isHttpException.shouldBeTrue()
}

fun ApiResponse<Any>.shouldBeNetworkException() {
    this.isNetworkException.shouldBeTrue()
}

fun ApiResponse<Any>.shouldBeUnknownError() {
    this.isUnKnownError.shouldBeTrue()
}