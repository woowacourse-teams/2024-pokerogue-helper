package poke.rogue.helper.presentation.dex.sort

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.data.model.PokemonSort

@Parcelize
enum class PokemonSortUiModel(val id: Int, val label: String) : Parcelable {
    ByDexNumber(0, "도감순"),
    ByBaseStat(1, "종족값순 ⭐️️"),
    BySpeed(2, "스피드순 ⭐️️"),
    ByAttack(3, "공격순"),
    ByDefense(4, "방어순"),
    BySpecialAttack(5, "특수공격순"),
    BySpecialDefense(6, "특수방어순"),
    ByHp(7, "HP순"),
}

fun PokemonSortUiModel.toData(): PokemonSort =
    when (this) {
        PokemonSortUiModel.ByDexNumber -> PokemonSort.ByDexNumber
        PokemonSortUiModel.ByBaseStat -> PokemonSort.ByBaseStat
        PokemonSortUiModel.BySpeed -> PokemonSort.BySpeed
        PokemonSortUiModel.ByAttack -> PokemonSort.ByAttack
        PokemonSortUiModel.ByDefense -> PokemonSort.ByDefense
        PokemonSortUiModel.BySpecialAttack -> PokemonSort.BySpecialAttack
        PokemonSortUiModel.BySpecialDefense -> PokemonSort.BySpecialDefense
        PokemonSortUiModel.ByHp -> PokemonSort.ByHp
    }

fun PokemonSort.toUiModel(): PokemonSortUiModel =
    when (this) {
        PokemonSort.ByDexNumber -> PokemonSortUiModel.ByDexNumber
        PokemonSort.ByBaseStat -> PokemonSortUiModel.ByBaseStat
        PokemonSort.BySpeed -> PokemonSortUiModel.BySpeed
        PokemonSort.ByAttack -> PokemonSortUiModel.ByAttack
        PokemonSort.ByDefense -> PokemonSortUiModel.ByDefense
        PokemonSort.BySpecialAttack -> PokemonSortUiModel.BySpecialAttack
        PokemonSort.BySpecialDefense -> PokemonSortUiModel.BySpecialDefense
        PokemonSort.ByHp -> PokemonSortUiModel.ByHp
    }
