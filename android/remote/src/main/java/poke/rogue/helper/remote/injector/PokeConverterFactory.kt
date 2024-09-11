package poke.rogue.helper.remote.injector

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import poke.rogue.helper.remote.dto.response.BaseResponse
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class PokeConverterFactory(
    private val json: Json,
    private val delegate: Converter.Factory,
) : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *> {
        return Converter { body: ResponseBody ->
            // type 이 BaseResponse 이라면 BaseResponse 로 변환
            if (getRawType(type) == BaseResponse::class.java) {
                return@Converter delegate.responseBodyConverter(type, annotations, retrofit)
                    ?.convert(body)
            }
            // unwrap BaseResponse
            val baseResponseJsonObject = json.parseToJsonElement(body.string()).jsonObject
            val dataPropertyName =
                baseResponseJsonObject.keys.lastOrNull() ?: error("No data property")
            val jsonString = baseResponseJsonObject[dataPropertyName].toString()
            // unwrap 한 data property
            val newBody = jsonString.toResponseBody(body.contentType())
            delegate.responseBodyConverter(type, annotations, retrofit)?.convert(newBody)
        }
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<*, RequestBody>? {
        return delegate.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit,
        )
    }
}
