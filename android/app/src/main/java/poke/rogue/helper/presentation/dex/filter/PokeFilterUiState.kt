package poke.rogue.helper.presentation.dex.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.presentation.type.model.TypeUiModel

@Parcelize
data class PokeFilterUiState(
    val types: List<SelectableUiModel<TypeUiModel>>,
    val generations: List<SelectableUiModel<PokeGenerationUiModel>>,
    val selectedTypes: List<TypeUiModel> = emptyList(),
) : Parcelable {
    init {
        require(generations.any { it.isSelected }) {
            "적어도 하나의 세대가 선택되어야 합니다."
        }
        require(generations.size == PokeGenerationUiModel.entries.size) {
            "세대의 크기는 ${PokeGenerationUiModel.entries.size}여야 합니다."
        }
        require(types.size == TypeUiModel.entries.size) {
            "타입의 크기는 ${TypeUiModel.entries.size}여야 합니다."
        }
        require(types.count { it.isSelected } <= 2) {
            "최대 2개의 타입만 선택할 수 있습니다."
        }
    }

    val selectedGeneration: PokeGenerationUiModel
        get() = generations.first { it.isSelected }.data

    companion object {
        val DEFAULT =
            PokeFilterUiState(
                types =
                    TypeUiModel.entries.mapIndexed { index, typeUiModel ->
                        SelectableUiModel(
                            index,
                            false,
                            typeUiModel,
                        )
                    },
                generations =
                    PokeGenerationUiModel.entries.mapIndexed { index, pokeGenerationUiModel ->
                        if (pokeGenerationUiModel == PokeGenerationUiModel.ALL) {
                            SelectableUiModel(
                                index,
                                true,
                                pokeGenerationUiModel,
                            )
                        } else {
                            SelectableUiModel(
                                index,
                                false,
                                pokeGenerationUiModel,
                            )
                        }
                    },
            )
    }
}
