package poke.rogue.helper.presentation.type.model

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.R
import poke.rogue.helper.data.model.Type

@Parcelize
enum class TypeUiModel(
    val id: Int,
    val typeName: String,
    @ColorRes val typeColor: Int,
    @DrawableRes val typeIconResId: Int,
) : Parcelable {
    NORMAL(0, "노말", R.color.poke_normal, R.drawable.icon_type_normal),
    FIRE(1, "불꽃", R.color.poke_fire, R.drawable.icon_type_fire),
    WATER(2, "물", R.color.poke_water, R.drawable.icon_type_water),
    ELECTRIC(3, "전기", R.color.poke_electric, R.drawable.icon_type_electric),
    GRASS(4, "풀", R.color.poke_grass, R.drawable.icon_type_grass),
    ICE(5, "얼음", R.color.poke_ice, R.drawable.icon_type_ice),
    FIGHTING(6, "격투", R.color.poke_fighting, R.drawable.icon_type_fighting),
    POISON(7, "독", R.color.poke_poison, R.drawable.icon_type_poison),
    GROUND(8, "땅", R.color.poke_ground, R.drawable.icon_type_ground),
    FLYING(9, "비행", R.color.poke_flying, R.drawable.icon_type_flying),
    PSYCHIC(10, "에스퍼", R.color.poke_psychic, R.drawable.icon_type_psychic),
    BUG(11, "벌레", R.color.poke_bug, R.drawable.icon_type_bug),
    ROCK(12, "바위", R.color.poke_rock, R.drawable.icon_type_rock),
    GHOST(13, "고스트", R.color.poke_ghost, R.drawable.icon_type_ghost),
    DRAGON(14, "드래곤", R.color.poke_dragon, R.drawable.icon_type_dragon),
    DARK(15, "악", R.color.poke_dark, R.drawable.icon_type_dark),
    STEEL(16, "강철", R.color.poke_steel, R.drawable.icon_type_steel),
    FAIRY(17, "페어리", R.color.poke_fairy, R.drawable.icon_type_fairy),
    STELLAR(18, "스텔라", R.color.poke_white, R.drawable.icon_type_stellar),
    ;

    companion object {
        fun fromId(id: Int): TypeUiModel {
            return entries.find { it.id == id } ?: throw IllegalArgumentException("Unknown type ID: $id")
        }
    }
}

fun Type.toUi(): TypeUiModel = TypeUiModel.valueOf(this.name)

fun TypeUiModel.toData(): Type = Type.fromId(this.id)
