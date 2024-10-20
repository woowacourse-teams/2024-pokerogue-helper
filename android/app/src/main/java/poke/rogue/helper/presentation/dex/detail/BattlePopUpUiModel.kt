package poke.rogue.helper.presentation.dex.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import poke.rogue.helper.R

sealed class BattlePopUpUiModel(
    @StringRes val description: Int,
    @DrawableRes val icon: Int,
) {
    companion object {
        val items =
            listOf(
                EnemyPokemon(
                    R.string.pokemon_detail_to_battle_with_enemy_pop_up_descriptions,
                    R.drawable.icon_home_battle,
                ),
                MyPokemon(
                    R.string.pokemon_detail_to_battle_with_mine_pop_up_descriptions,
                    R.drawable.icon_home_battle,
                ),
            )
    }
}

class MyPokemon(description: Int, icon: Int) : BattlePopUpUiModel(description, icon)

class EnemyPokemon(description: Int, icon: Int) : BattlePopUpUiModel(description, icon)
