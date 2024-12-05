package poke.rogue.helper.presentation.dex.sort

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.R
import poke.rogue.helper.data.model.PokemonSort

@Parcelize
enum class PokemonSortUiModel(
    val id: Int,
    @StringRes val label: Int,
) : Parcelable {
    ByDexNumber(0, R.string.by_pokedex_number),
    ByBaseStat(1, R.string.by_base_stats),
    BySpeed(2, R.string.by_speed),
    ByAttack(3, R.string.by_attack),
    ByDefense(4, R.string.by_defense),
    BySpecialAttack(5, R.string.by_special_attack),
    BySpecialDefense(6, R.string.by_special_defense),
    ByHp(7, R.string.by_hp),
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
