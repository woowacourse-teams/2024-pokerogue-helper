package poke.rogue.helper.data.model

@JvmInline
value class Type private constructor(val name: String) {
    companion object {
        private val TYPES: MutableMap<String, Type> =
            mutableMapOf(
                "노말" to Type("노말"),
                "불" to Type("불"),
                "물" to Type("물"),
                "전기" to Type("전기"),
                "풀" to Type("풀"),
                "얼음" to Type("얼음"),
                "격투" to Type("격투"),
                "독" to Type("독"),
                "땅" to Type("땅"),
                "비행" to Type("비행"),
                "에스퍼" to Type("에스퍼"),
                "벌레" to Type("벌레"),
                "돌" to Type("돌"),
                "고스트" to Type("고스트"),
                "드래곤" to Type("드래곤"),
                "악" to Type("악"),
                "강철" to Type("강철"),
                "페어리" to Type("페어리"),
            )

        fun of(name: String): Type = TYPES.getOrPut(name) { Type(name) }
    }
}
