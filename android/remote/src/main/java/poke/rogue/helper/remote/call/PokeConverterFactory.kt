package poke.rogue.helper.remote.call//package poke.rogue.helper.remote
//
//import kotlinx.serialization.json.Json
//import kotlinx.serialization.serializer
//import okhttp3.RequestBody
//import okhttp3.ResponseBody
//import poke.rogue.helper.remote.dto.response.BaseResponse
//import retrofit2.Converter
//import retrofit2.Retrofit
//import java.lang.reflect.Type
//
//class PokeConverterFactory(
//    private val json: Json,
//    private val converter: Converter.Factory
//) : Converter.Factory() {
//
//    override fun responseBodyConverter(
//        type: Type,
//        annotations: Array<Annotation>,
//        retrofit: Retrofit
//    ): Converter<ResponseBody, *> {
//        val serializer = serializer(type)
//        return Converter { body ->
//            val rawJson = body.string()
//            val baseResponse = json.decodeFromString(BaseResponse.serializer(), rawJson)
//            val decoded = converter.responseBodyConverter(
//                BaseResponse.serializer(serializer),
//                annotations,
//                retrofit
//            )?.convert(body)
//            val baseResponse = json.decodeFromString(converter.stringConverter(), rawJson)
////            baseResponse.data
//        }
//    }
//
//    override fun requestBodyConverter(
//        type: Type,
//        parameterAnnotations: Array<out Annotation>,
//        methodAnnotations: Array<out Annotation>,
//        retrofit: Retrofit
//    ): Converter<*, RequestBody>? {
//        return converter.requestBodyConverter(
//            type,
//            parameterAnnotations,
//            methodAnnotations,
//            retrofit
//        )
//    }
//}
