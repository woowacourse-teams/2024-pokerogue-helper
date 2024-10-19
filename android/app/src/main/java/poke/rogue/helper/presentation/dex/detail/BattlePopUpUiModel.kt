package poke.rogue.helper.presentation.dex.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import poke.rogue.helper.R

data class BattlePopUpUiModel(
    @StringRes val description: Int,
    @DrawableRes val icon: Int,
) {
    companion object {
        val items = listOf(
            BattlePopUpUiModel(
                R.string.pokemon_detail_to_battle_with_enemy_pop_up_descriptions,
                R.drawable.ic_pokemon_battle_enemy
            ),
            BattlePopUpUiModel(
                R.string.pokemon_detail_to_battle_with_mine_pop_up_descriptions,
                R.drawable.ic_pokemon_battle_mine
            ),
        )
    }
}

