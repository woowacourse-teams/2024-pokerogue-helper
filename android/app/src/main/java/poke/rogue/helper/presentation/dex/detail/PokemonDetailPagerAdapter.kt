package poke.rogue.helper.presentation.dex.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PokemonDetailPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments =
        listOf(
            PokemonStatFragment(),
            PokemonEvolutionFragment(),
            PokemonMovesFragment(),
            PokemonInformationFragment(),
        )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
