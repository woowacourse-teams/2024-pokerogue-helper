package poke.rogue.helper.presentation.ability.detail

class AbilityDetailUiModel(
    val id: Int,
    val title: String,
    val fullDescription: String,
) {
    companion object {
        private const val DUMMY_ABILITY_NAME = "악취"
        private const val DUMMY_ABILITY_DESCRIPTION = "악취를 풍겨 상대방의 특성을 무효화 시킵니다."

        val DUMMY =
            AbilityDetailUiModel(
                id = -1,
                title = DUMMY_ABILITY_NAME,
                fullDescription = DUMMY_ABILITY_DESCRIPTION,
            )
    }
}
