package poke.rogue.helper.remote.interceptor

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.util.Locale

class LocaleInterceptorTest : BehaviorSpec({

    val mockWebServer = MockWebServer()

    beforeSpec {
        mockWebServer.start()
    }

    afterSpec {
        mockWebServer.shutdown()
    }

    forAll(
        table(
            headers("description", "testLocale", "expectedHeader"),
            row("ko-KR", Locale("ko", "KR"), "ko-KR"),
            row("en-US", Locale("en", "US"), "en-US"),
        ),
    ) { description, testLocale, expectedHeader ->
        Given("디바이스 로케일이 $description 일 때") {
            Locale.setDefault(testLocale)

            val client =
                OkHttpClient.Builder()
                    .addInterceptor(LocaleInterceptor())
                    .build()
            val request =
                Request.Builder()
                    .url(mockWebServer.url("/test"))
                    .build()
            mockWebServer.enqueue(MockResponse().setResponseCode(200))

            When("api 요청이 불릴 때") {
                client.newCall(request).execute()

                Then("헤더에 들어가는 Accept-Language 는 $expectedHeader 이다.") {
                    val recordedRequest = mockWebServer.takeRequest()
                    val headerValue = recordedRequest.getHeader("Accept-Language")
                    headerValue shouldBe expectedHeader
                }
            }
        }
    }
})
