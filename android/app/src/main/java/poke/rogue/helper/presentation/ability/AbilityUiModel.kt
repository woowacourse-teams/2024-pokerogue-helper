package poke.rogue.helper.presentation.ability

data class AbilityUiModel(
    val id: Int,
    val title: String,
    val description: String,
) {
    companion object {
        private const val DUMMY_ABILITY_NAME = "악취"
        private const val DUMMY_ABILITY_DESCRIPTION = "상대방의 특성을 무효화한다."

        val DUMMY =
            AbilityUiModel(
                id = -1,
                title = DUMMY_ABILITY_NAME,
                description = DUMMY_ABILITY_DESCRIPTION,
            )
    }
}
