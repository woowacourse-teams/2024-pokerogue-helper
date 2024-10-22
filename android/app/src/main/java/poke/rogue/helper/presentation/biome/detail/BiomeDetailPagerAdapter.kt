package poke.rogue.helper.presentation.biome.detail

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

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int) =
        when (position) {
            0 -> BiomeGymFragment()
            1 -> BiomeBossFragment()
            2 -> BiomeWildPokemonFragment()
            3 -> BiomeNextBiomesFragment()
            else -> error("그런건 없단다 - position : $position 에는 해당하는 Fragment가 없습니다.")
        }
}
