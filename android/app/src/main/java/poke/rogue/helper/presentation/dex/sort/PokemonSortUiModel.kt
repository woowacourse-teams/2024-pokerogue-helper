package poke.rogue.helper.presentation.dex.sort

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.R
import poke.rogue.helper.data.model.PokemonSort

@Parcelize
enum class PokemonSortUiModel(val id: Int, val label: String) : Parcelable {
    ByDexNumber(0, "도감순"),
    ByBaseStat(1, "종족값순 ⭐️️"),
    BySpeed(2, "스피드순 ⭐️️"),
    ByAttack(3, "공격력순"),
    ByDefense(4, "방어력순"),
    BySpecialAttack(5, "특수공격순"),
    BySpecialDefense(6, "특수방어순"),
    ByHp(7, "HP순"),
}

@Parcelize
enum class PokemonSortUiModel1(val id: Int, @StringRes val label: Int) : Parcelable {
    ByDexNumber(0, R.string.by_pokedex_number),
    ByBaseStat(1, R.string.by_base_stats),
    BySpeed(2, R.string.by_speed),
    ByAttack(3, R.string.by_attack),
    ByDefense(4, R.string.by_defense),
    BySpecialAttack(5, R.string.by_special_attack),
    BySpecialDefense(6, R.string.by_special_defense),
    ByHp(7, R.string.by_hp),
}

fun PokemonSortUiModel1.toData(): PokemonSort =
    when (this) {
        PokemonSortUiModel1.ByDexNumber -> PokemonSort.ByDexNumber
        PokemonSortUiModel1.ByBaseStat -> PokemonSort.ByBaseStat
        PokemonSortUiModel1.BySpeed -> PokemonSort.BySpeed
        PokemonSortUiModel1.ByAttack -> PokemonSort.ByAttack
        PokemonSortUiModel1.ByDefense -> PokemonSort.ByDefense
        PokemonSortUiModel1.BySpecialAttack -> PokemonSort.BySpecialAttack
        PokemonSortUiModel1.BySpecialDefense -> PokemonSort.BySpecialDefense
        PokemonSortUiModel1.ByHp -> PokemonSort.ByHp
    }

fun PokemonSort.toUiModel(): PokemonSortUiModel1 =
    when (this) {
        PokemonSort.ByDexNumber -> PokemonSortUiModel1.ByDexNumber
        PokemonSort.ByBaseStat -> PokemonSortUiModel1.ByBaseStat
        PokemonSort.BySpeed -> PokemonSortUiModel1.BySpeed
        PokemonSort.ByAttack -> PokemonSortUiModel1.ByAttack
        PokemonSort.ByDefense -> PokemonSortUiModel1.ByDefense
        PokemonSort.BySpecialAttack -> PokemonSortUiModel1.BySpecialAttack
        PokemonSort.BySpecialDefense -> PokemonSortUiModel1.BySpecialDefense
        PokemonSort.ByHp -> PokemonSortUiModel1.ByHp
    }
