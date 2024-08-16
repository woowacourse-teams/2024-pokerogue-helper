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

        TabLayoutMediator(binding.tablayoutBiomeDetail, binding.vpBiome) { tab, position ->
            when (position) {
                0 -> tab.text = "출현 포켓몬"
                1 -> tab.text = "보스"
                2 -> tab.text = "다음 바이옴"
                3 -> tab.text = "관장"
            }
        }.attach()
    }

    companion object {
        const val BIOME_ID = "biomeId"
    }
}
