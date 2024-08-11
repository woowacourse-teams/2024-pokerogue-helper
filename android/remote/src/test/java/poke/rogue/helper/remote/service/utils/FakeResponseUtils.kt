package poke.rogue.helper.remote.service.utils

import okhttp3.mockwebserver.MockResponse
import java.io.File

fun successResponse(fileName: String): MockResponse {
    val matchingJson = File("src/test/res/${fileName}.json").readText()
    return MockResponse().setBody(matchingJson).setResponseCode(200)
}

fun httpErrorResponse(code: Int): MockResponse {
    return MockResponse().setResponseCode(code)
}