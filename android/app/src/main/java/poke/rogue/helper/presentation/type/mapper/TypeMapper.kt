package poke.rogue.helper.presentation.type.mapper

import poke.rogue.helper.R
import poke.rogue.helper.data.model.TypeInfo
import poke.rogue.helper.presentation.type.model.TypeUiModel

fun TypeInfo.toSelectionUiModel(): TypeUiModel {
    val imageResId =
        when (this.id) {
            0 -> R.drawable.img_type_tmp_1
            1 -> R.drawable.img_type_tmp_1
            2 -> R.drawable.img_type_tmp_1
            3 -> R.drawable.img_type_tmp_1
            4 -> R.drawable.img_type_tmp_1
            5 -> R.drawable.img_type_tmp_1
            6 -> R.drawable.img_type_tmp_1
            7 -> R.drawable.img_type_tmp_1
            8 -> R.drawable.img_type_tmp_1
            9 -> R.drawable.img_type_tmp_1
            10 -> R.drawable.img_type_tmp_1
            11 -> R.drawable.img_type_tmp_1
            12 -> R.drawable.img_type_tmp_1
            13 -> R.drawable.img_type_tmp_1
            14 -> R.drawable.img_type_tmp_1
            15 -> R.drawable.img_type_tmp_1
            16 -> R.drawable.img_type_tmp_1
            17 -> R.drawable.img_type_tmp_1
            else -> throw IllegalArgumentException("Unknown type ID")
        }
    return TypeUiModel(this.id, this.name, imageResId)
}

fun TypeInfo.toResultUiModel(): TypeUiModel {
    val imageResId =
        when (this.id) {
            0 -> R.drawable.img_property_tmp_1
            1 -> R.drawable.img_property_tmp_1
            2 -> R.drawable.img_property_tmp_1
            3 -> R.drawable.img_property_tmp_1
            4 -> R.drawable.img_property_tmp_1
            5 -> R.drawable.img_property_tmp_1
            6 -> R.drawable.img_property_tmp_1
            7 -> R.drawable.img_property_tmp_1
            8 -> R.drawable.img_property_tmp_1
            9 -> R.drawable.img_property_tmp_1
            10 -> R.drawable.img_property_tmp_1
            11 -> R.drawable.img_property_tmp_1
            12 -> R.drawable.img_property_tmp_1
            13 -> R.drawable.img_property_tmp_1
            14 -> R.drawable.img_property_tmp_1
            15 -> R.drawable.img_property_tmp_1
            16 -> R.drawable.img_property_tmp_1
            17 -> R.drawable.img_property_tmp_1
            else -> throw IllegalArgumentException("Unknown type ID")
        }
    return TypeUiModel(this.id, this.name, imageResId)
}
