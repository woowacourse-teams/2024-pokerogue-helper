package poke.rogue.helper.remote

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import poke.rogue.helper.BuildConfig
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitModule {
    private val retrofit by lazy {
        val converterFactory = jsonConverterFactory(json())
        val client = okHttpClient(loggingInterceptor())
        Retrofit.Builder()
            .baseUrl(BuildConfig.POKE_BASE_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun json(): Json =
        Json {
            coerceInputValues = true
        }

    private fun jsonConverterFactory(json: Json): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    private fun loggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            },
        )

    private fun okHttpClient(logInterceptor: Interceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(logInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS).build()

    fun retrofit(): Retrofit = retrofit
}
