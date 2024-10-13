package poke.rogue.helper.remote.di

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import poke.rogue.helper.remote.BuildConfig
import poke.rogue.helper.remote.injector.PokeCallAdapterFactory
import poke.rogue.helper.remote.injector.PokeConverterFactory
import poke.rogue.helper.remote.interceptor.RedirectInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

private const val LOCAL_HOST_BASE_URL = "http://10.0.2.2:8080"

internal val retrofitModule
    get() = module {

    single<Json> {
        if (BuildConfig.DEBUG) {
            Json {
                coerceInputValues = true
            }
        } else {
            Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
        }
    }
    single<Converter.Factory> { get<Json>().asConverterFactory("application/json".toMediaType()) }
    single {
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            },
        )
    }
    single { (redirectUrl: String) ->
        RedirectInterceptor(redirectUrl)
    }
    singleOf(::PokeCallAdapterFactory)
    singleOf(::PokeConverterFactory)

    single<OkHttpClient> {
        OkHttpClient
            .Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .let { client ->
                if (BuildConfig.DEBUG) {
                    client.addInterceptor(get<RedirectInterceptor> { parametersOf(LOCAL_HOST_BASE_URL) })
                } else {
                    client
                }
            }.connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.POKE_BASE_URL)
            .client(get())
            .addCallAdapterFactory(get<PokeCallAdapterFactory>())
            .addConverterFactory(get<PokeConverterFactory>())
            .build()
    }
}