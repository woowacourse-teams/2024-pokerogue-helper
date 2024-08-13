package poke.rogue.helper.presentation.biome

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import poke.rogue.helper.presentation.biome.detail.BiomeBossPokemonFragment
import poke.rogue.helper.presentation.biome.detail.map.BiomeMapFragment
import poke.rogue.helper.presentation.biome.detail.BiomeWildPokemonFragment

class BiomeDetailPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
    private val fragment = listOf(
        BiomeWildPokemonFragment(),
        BiomeBossPokemonFragment(),
        BiomeMapFragment(),
    )

    override fun getItemCount(): Int = fragment.size
    override fun createFragment(position: Int) = fragment[position]
}
