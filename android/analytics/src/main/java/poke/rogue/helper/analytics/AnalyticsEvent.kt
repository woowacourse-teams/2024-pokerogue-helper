package poke.rogue.helper.analytics

/**
 * 분석 이벤트를 나타냅니다.
 *
 * @param type - 이벤트 유형. 가능한 경우 표준 이벤트 `Types` 중 하나를 사용하세요.
 * 사용자 정의 이벤트를 정의할 수 있습니다 (예: Firebase Analytics 사용자 정의 이벤트 생성).
 *
 * @param extras - 이벤트에 추가적인 컨텍스트를 제공하는 매개변수 목록. (선택 사항)
 *
 * ref: https://github.com/android/nowinandroid/blob/main/core/analytics/src/main/kotlin/com/google/samples/apps/nowinandroid/core/analytics/AnalyticsEvent.kt
 */
data class AnalyticsEvent(
    val type: String,
    val extras: List<Param> = emptyList(),
) {
    data object Types {
        const val SCREEN_VIEW = "screen_view"
        const val ACTION = "select_content"
    }

    /**
     * 분석 이벤트에 추가 컨텍스트를 제공하기 위해 사용되는 키-값 쌍.
     *
     * @param key - 매개변수 키. 가능한 경우 표준 `ParamKeys` 중 하나를 사용하세요.
     * 그러나, 적합한 키가 없을 경우 백엔드 분석 시스템에 구성된 키를 사용하여 직접 정의할 수 있습니다.
     * (예: Firebase Analytics 사용자 정의 매개변수 생성).
     *
     * @param value - 매개변수 값.
     */
    data class Param(val key: String, val value: String)

    data object ParamKeys {
        const val SCREEN_NAME = "screen_name"
        const val ACTION_NAME = "action_name"
    }
}
