package poke.rogue.helper.presentation.biome

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import poke.rogue.helper.presentation.biome.detail.boss.BiomeBossFragment
import poke.rogue.helper.presentation.biome.detail.gym.BiomeGymFragment
import poke.rogue.helper.presentation.biome.detail.nextbiomes.BiomeNextBiomesFragment
import poke.rogue.helper.presentation.biome.detail.wild.BiomeWildPokemonFragment

class BiomeDetailPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
    private val fragment =
        listOf(
            BiomeGymFragment(),
            BiomeBossFragment(),
            BiomeWildPokemonFragment(),
            BiomeNextBiomesFragment(),
        )

    override fun getItemCount(): Int = fragment.size

    override fun createFragment(position: Int) = fragment[position]
}
