package poke.rogue.helper.presentation.battle.model

import poke.rogue.helper.presentation.type.model.TypeUiModel

data class SkillSelectionUiModel(
    val id: String,
    val name: String,
    val type: TypeUiModel,
    val category: String,
) {
    companion object {
        val DUMMY =
            listOf(
                SkillSelectionUiModel(
                    id = "1",
                    name = "몸통박치기",
                    type = TypeUiModel.NORMAL,
                    category = "Physical Attack",
                ),
                SkillSelectionUiModel(
                    id = "2",
                    name = "울음소리",
                    type = TypeUiModel.NORMAL,
                    category = "Status Change",
                ),
                SkillSelectionUiModel(
                    id = "3",
                    name = "덩굴채찍",
                    type = TypeUiModel.GRASS,
                    category = "Physical Attack",
                ),
                SkillSelectionUiModel(
                    id = "4",
                    name = "성장",
                    type = TypeUiModel.NORMAL,
                    category = "Status Change",
                ),
                SkillSelectionUiModel(
                    id = "5",
                    name = "씨뿌리기",
                    type = TypeUiModel.GRASS,
                    category = "Status Change",
                ),
            )
    }
}
