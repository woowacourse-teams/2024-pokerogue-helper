package poke.rogue.helper.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

class RedirectInterceptor(private val url: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response =
            try {
                chain.proceed(request)
            } catch (e: IOException) {
                Timber.e("개발 서버 연결 실패: ${request.url}")
                Timber.e("로컬 서버로 재시도: $url")
                val path: String = request.url.encodedPath
                val localhostRequest: Request =
                    request.newBuilder()
                        .url(url + path)
                        .build()
                chain.proceed(localhostRequest)
            }
        return response
    }
}
