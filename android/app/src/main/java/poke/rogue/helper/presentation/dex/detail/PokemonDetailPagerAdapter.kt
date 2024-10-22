package poke.rogue.helper.presentation.dex.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import poke.rogue.helper.presentation.dex.detail.evolution.PokemonEvolutionFragment
import poke.rogue.helper.presentation.dex.detail.information.PokemonInformationFragment
import poke.rogue.helper.presentation.dex.detail.skill.PokemonDetailSkillFragment
import poke.rogue.helper.presentation.dex.detail.stat.PokemonStatFragment

class PokemonDetailPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments =
        listOf(
            PokemonStatFragment(),
            PokemonEvolutionFragment(),
            PokemonDetailSkillFragment(),
            PokemonInformationFragment(),
        )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
