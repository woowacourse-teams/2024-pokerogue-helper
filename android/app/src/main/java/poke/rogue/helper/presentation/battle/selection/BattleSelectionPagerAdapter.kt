package poke.rogue.helper.presentation.battle.selection

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import poke.rogue.helper.presentation.battle.selection.pokemon.PokemonSelectionFragment
import poke.rogue.helper.presentation.battle.selection.skill.SkillSelectionFragment

class BattleSelectionPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val fragments =
        listOf(
            PokemonSelectionFragment(),
            SkillSelectionFragment(),
        )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
