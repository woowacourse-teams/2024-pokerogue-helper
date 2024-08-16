package poke.rogue.helper.presentation.biome.detail

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayoutMediator
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBiomeDetailBinding
import poke.rogue.helper.presentation.biome.BiomeDetailPagerAdapter
import poke.rogue.helper.presentation.biome.model.BiomeUiModel
import poke.rogue.helper.presentation.toolbar.ToolbarActivity

class BiomeDetailActivity :
    ToolbarActivity<ActivityBiomeDetailBinding>(R.layout.activity_biome_detail) {
    private lateinit var pagerAdapter: BiomeDetailPagerAdapter

    override val toolbar: Toolbar
        get() = binding.toolbarBiomeDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val biomeId = intent.getIntExtra(BIOME_ID, 1)
        initAdapter()

    }

    private fun initAdapter() {
        val detail = BiomeUiModel.DUMMYS[1]
        binding.biomeUiModel = detail

        pagerAdapter = BiomeDetailPagerAdapter(this)
        binding.vpBiome.apply {
            adapter = pagerAdapter
        }

        val tabTitles = resources.getStringArray(R.array.biome_tab_titles)
        TabLayoutMediator(binding.tablayoutBiomeDetail, binding.vpBiome) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    companion object {
        const val BIOME_ID = "biomeId"
    }
}
