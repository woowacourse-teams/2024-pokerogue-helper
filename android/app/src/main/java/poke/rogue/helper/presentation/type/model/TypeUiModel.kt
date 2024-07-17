package poke.rogue.helper.presentation.type.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import poke.rogue.helper.R
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.model.TypeInfo

enum class TypeUiModel(
    val id: Int,
    val typeName: String,
    @ColorRes val typeColor: Int,
    @DrawableRes val typeIconResId: Int,
) {
    NORMAL(0, "노말", R.color.poke_normal, R.drawable.icon_type_normal),
    FIRE(1, "불꽃", R.color.poke_fire, R.drawable.icon_type_fire),
    WATER(2, "물", R.color.poke_water, R.drawable.icon_type_water),
    ELECTRIC(3, "전기", R.color.poke_electric, R.drawable.icon_type_electric),
    GRASS(4, "풀", R.color.poke_grass, R.drawable.icon_type_green),
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
    ;

    companion object {
        fun fromId(id: Int): TypeUiModel? {
            return entries.find { it.id == id }
        }

        fun fromName(typeName: String): TypeUiModel? {
            return entries.find { it.typeName.equals(typeName, ignoreCase = true) }
        }

        fun fromType(type: Type): TypeUiModel? =
            when (type.name) {
                "normal" -> NORMAL
                "fire" -> FIRE
                "water" -> WATER
                "electric" -> ELECTRIC
                "grass" -> GRASS
                "ice" -> ICE
                "fighting" -> FIGHTING
                "poison" -> POISON
                "ground" -> GROUND
                "flying" -> FLYING
                "psychic" -> PSYCHIC
                "bug" -> BUG
                "rock" -> ROCK
                "ghost" -> GHOST
                "dragon" -> DRAGON
                "dark" -> DARK
                "steel" -> STEEL
                "fairy" -> FAIRY
                else -> null
            }

        fun TypeInfo.toUiModel(): TypeUiModel {
            return fromId(id)
                ?: throw IllegalArgumentException("Unknown type ID: $id")
        }
    }
}
