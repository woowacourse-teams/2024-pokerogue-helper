package poke.rogue.helper.presentation.type.model

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.R
import poke.rogue.helper.data.model.Type

@Parcelize
enum class TypeUiModel(
    val id: Int,
    @StringRes val typeName: Int,
    @ColorRes val typeColor: Int,
    @DrawableRes val typeIconResId: Int,
) : Parcelable {
    NORMAL(0, R.string.type_matching_type_normal, R.color.poke_normal, R.drawable.icon_type_normal),
    FIRE(1, R.string.type_matching_type_fire, R.color.poke_fire, R.drawable.icon_type_fire),
    WATER(2, R.string.type_matching_type_water, R.color.poke_water, R.drawable.icon_type_water),
    ELECTRIC(3, R.string.type_matching_type_electric, R.color.poke_electric, R.drawable.icon_type_electric),
    GRASS(4, R.string.type_matching_type_grass, R.color.poke_grass, R.drawable.icon_type_grass),
    ICE(5, R.string.type_matching_type_ice, R.color.poke_ice, R.drawable.icon_type_ice),
    FIGHTING(6, R.string.type_matching_type_fighting, R.color.poke_fighting, R.drawable.icon_type_fighting),
    POISON(7, R.string.type_matching_type_poison, R.color.poke_poison, R.drawable.icon_type_poison),
    GROUND(8, R.string.type_matching_type_ground, R.color.poke_ground, R.drawable.icon_type_ground),
    FLYING(9, R.string.type_matching_type_flying, R.color.poke_flying, R.drawable.icon_type_flying),
    PSYCHIC(10, R.string.type_matching_type_psychic, R.color.poke_psychic, R.drawable.icon_type_psychic),
    BUG(11, R.string.type_matching_type_bug, R.color.poke_bug, R.drawable.icon_type_bug),
    ROCK(12, R.string.type_matching_type_rock, R.color.poke_rock, R.drawable.icon_type_rock),
    GHOST(13, R.string.type_matching_type_ghost, R.color.poke_ghost, R.drawable.icon_type_ghost),
    DRAGON(14, R.string.type_matching_type_dragon, R.color.poke_dragon, R.drawable.icon_type_dragon),
    DARK(15, R.string.type_matching_type_dark, R.color.poke_dark, R.drawable.icon_type_dark),
    STEEL(16, R.string.type_matching_type_steel, R.color.poke_steel, R.drawable.icon_type_steel),
    FAIRY(17, R.string.type_matching_type_fairy, R.color.poke_fairy, R.drawable.icon_type_fairy),
    STELLAR(18, R.string.type_matching_type_stellar, R.color.poke_white, R.drawable.icon_type_stellar),
    ;

    companion object {
        fun fromId(id: Int): TypeUiModel {
            return entries.find { it.id == id } ?: throw IllegalArgumentException("Unknown type ID: $id")
        }
    }
}

fun Type.toUi(): TypeUiModel = TypeUiModel.valueOf(this.name)

fun TypeUiModel.toData(): Type = Type.fromId(this.id)

fun List<Type>.toUi(): List<TypeUiModel> = map(Type::toUi)
